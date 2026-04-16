package com.edts.booking.service.impl;

import com.edts.booking.exception.BadRequestException;
import com.edts.booking.exception.BookingException;
import com.edts.booking.exception.NotFoundException;
import com.edts.booking.model.dto.request.AddNewBooking;
import com.edts.booking.model.dto.request.BookingEventSeat;
import com.edts.booking.model.entity.*;
import com.edts.booking.repositories.BookingDetailRepository;
import com.edts.booking.repositories.BookingRepository;
import com.edts.booking.repositories.EventSeatRepository;
import com.edts.booking.service.BookingService;
import com.edts.booking.service.CustomerService;
import com.edts.booking.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingDetailRepository bookingDetailRepository;
    private final EventService eventService;
    private final CustomerService customerService;
    private final EventSeatRepository eventSeatRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, BookingDetailRepository bookingDetailRepository, EventService eventService, CustomerService customerService, EventSeatRepository eventSeatRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingDetailRepository = bookingDetailRepository;
        this.eventService = eventService;
        this.customerService = customerService;
        this.eventSeatRepository = eventSeatRepository;
    }

    @Override
    @Transactional
    public void add(AddNewBooking request) {
        CustomerEntity customerEntity = customerService.getCustomer(request.customerId());
        validateBooking(request);
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setCustomer(customerEntity);
        bookingEntity.setTotalAmount(request.totalAmount());
        bookingEntity.setCreatedBy("SYSTEM");
        bookingEntity.setStatus("PAID");
        bookingRepository.save(bookingEntity);

        for (BookingEventSeat bookingEventSeat : request.eventSeat()) {
            BookingDetailEntity bookingDetailEntity = new BookingDetailEntity();
            EventSeatEntity eventSeatEntity = eventService.getEventSeat(bookingEventSeat.eventSeatId());
            bookingDetailEntity.setEventSeatEntity(eventSeatEntity);
            bookingDetailEntity.setQty(bookingEventSeat.qty());
            BigDecimal totalPrice = eventSeatEntity.getPrice().multiply(BigDecimal.valueOf(bookingEventSeat.qty()));
            bookingDetailEntity.setPrice(totalPrice);
            bookingDetailEntity.setBooking(bookingEntity);
            bookingDetailEntity.setCreatedBy("SYSTEM");
            bookingDetailRepository.save(bookingDetailEntity);
            int bookingResult = eventService.bookSeat(eventSeatEntity.getEventSeatId(), bookingEventSeat.qty());
            if (bookingResult == 0) {
                throw new BookingException("Seats are sold out");
            }
        }
    }

    private void validateBooking(AddNewBooking request) {
        Instant now = Instant.now();

        List<Long> seatIds = request.eventSeat()
                .stream()
                .map(BookingEventSeat::eventSeatId)
                .distinct()
                .toList();

        List<EventSeatEntity> eventSeatEntities = eventSeatRepository
                .findAllById(seatIds);

        if (eventSeatEntities.isEmpty()) {
            log.error("event seat is empty");
            throw new NotFoundException("event seat is empty");
        }

        Map<Long, EventSeatEntity> eventSeatMappedById = eventSeatEntities.stream()
                .collect(Collectors.toMap(
                        EventSeatEntity::getEventSeatId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));

        BigDecimal totalAmountCalculated = new BigDecimal(0);

        //check is booking ticket all sold out or not
        for (BookingEventSeat bookingEventSeat : request.eventSeat()){
            EventSeatEntity eventSeatEntity = eventSeatMappedById.get(bookingEventSeat.eventSeatId());

            if (eventSeatEntity.getBookedCount().equals(eventSeatEntity.getTotalSeat())){
                String errMsg = "seat already booked for event " + eventSeatEntity.getEvent().getEventTitle();
                throw new BadRequestException(errMsg);
            }

            if ((eventSeatEntity.getBookedCount() + bookingEventSeat.qty()) > eventSeatEntity.getTotalSeat()){
                int availableSeats = eventSeatEntity.getTotalSeat() - eventSeatEntity.getBookedCount();
                String errMsg = "failed booking for event " + eventSeatEntity.getEvent().getEventTitle() + " , seat available: " + availableSeats;
                throw new BadRequestException(errMsg);
            }
            totalAmountCalculated = totalAmountCalculated.add(eventSeatEntity.getPrice().multiply(BigDecimal.valueOf(bookingEventSeat.qty())));
        }

        //validate is it still in window time
        for (Long requestedSeatIds : seatIds) {
            EventSeatEntity eventSeat = eventSeatMappedById.get(requestedSeatIds);
            Instant startDate = eventSeat.getEvent().getStartDate();
            Instant endDate = eventSeat.getEvent().getEndDate();
            if (!(now.isAfter(startDate) && now.isBefore(endDate))) {
                throw new BadRequestException("booking time for this event is closed");
            }
        }

        //check payment
        if (totalAmountCalculated.compareTo(request.totalAmount()) != 0){
            throw new BadRequestException("total payment is wrong");
        }

    }

    @Override
    @Transactional
    public void cancel(Long bookingId) {
        BookingEntity bookingEntity = bookingRepository.getReferenceById(bookingId);
        if (bookingEntity.getBookingId() == null) {
            log.error("no booking with ID {} found!", bookingId);
            throw new BadRequestException("booking ID not found");
        }
        BookingDetailEntity bookingDetailEntity = bookingDetailRepository.findByBooking_BookingId(bookingId);
        EventSeatEntity eventSeatEntity = bookingDetailEntity.getEventSeatEntity();
        bookingEntity.setStatus("CANCEL");
        int decreaseBookedCount = eventSeatEntity.getBookedCount() - bookingDetailEntity.getQty();
        eventSeatEntity.setBookedCount(decreaseBookedCount);
        eventSeatEntity.setModifiedBy("SYSTEM");
        bookingEntity.setModifiedBy("SYSTEM");
        bookingEntity.setModifiedAt(LocalDateTime.now());
        eventSeatEntity.setModifiedAt(LocalDateTime.now());
        eventSeatRepository.save(eventSeatEntity);
        bookingRepository.save(bookingEntity);
    }

}
