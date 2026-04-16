package com.edts.booking.service.impl;

import com.edts.booking.exception.BadRequestException;
import com.edts.booking.exception.NotFoundException;
import com.edts.booking.model.dto.request.AddNewEvent;
import com.edts.booking.model.dto.request.AddNewEventSeat;
import com.edts.booking.model.dto.response.EventResponse;
import com.edts.booking.model.dto.response.EventSeatResponse;
import com.edts.booking.model.entity.EventEntity;
import com.edts.booking.model.entity.EventSeatEntity;
import com.edts.booking.repositories.EventRepository;
import com.edts.booking.repositories.EventSeatRepository;
import com.edts.booking.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventSeatRepository eventSeatRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, EventSeatRepository eventSeatRepository) {
        this.eventRepository = eventRepository;
        this.eventSeatRepository = eventSeatRepository;
    }

    @Override
    public void add(AddNewEvent request) {
        try {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventTitle(request.eventTitle().trim());
            eventEntity.setEventType(request.eventType().trim());
            eventEntity.setEventVenue(request.eventVenue().trim());
            eventEntity.setCreatedBy("SYSTEM");
            eventEntity.setStartDate(request.startDate().toInstant());
            eventEntity.setEndDate(request.endDate().toInstant());
            eventRepository.save(eventEntity);
        } catch (Exception ex) {
            log.error("error creating new event: {}", ex.getMessage());
        }
    }

    @Override
    public void addSeat(Long eventId, AddNewEventSeat request) {
        try {
            EventEntity eventEntity = eventRepository.getReferenceById(eventId);
            EventSeatEntity eventSeatEntity = new EventSeatEntity();
            eventSeatEntity.setEvent(eventEntity);
            eventSeatEntity.setTotalSeat(request.totalSeat());
            eventSeatEntity.setEventSeatType(request.eventSeatType());
            eventSeatEntity.setPrice(request.price());
            eventSeatEntity.setCreatedBy("SYSTEM");
            eventSeatEntity.setBookedCount(0);
            eventSeatRepository.save(eventSeatEntity);
        } catch (Exception ex) {
            log.error("error creating new event seat: {}", ex.getMessage());
            throw new BadRequestException("failed creating new event seat");
        }

    }

    @Override
    public int bookSeat(Long eventSeatId, int qty) {
        return eventSeatRepository.bookSeat(eventSeatId, qty);
    }

    @Override
    public EventSeatEntity getEventSeat(Long eventSeatId) {
        Optional<EventSeatEntity> eventSeatEntity = eventSeatRepository.findById(eventSeatId);
        return eventSeatEntity.orElseThrow(() -> new NotFoundException("event not found"));
    }

    @Override
    public List<EventSeatResponse> getEventSeatByEventId(Long eventId) {
        List<EventSeatEntity> result = eventSeatRepository.findByEvent_EventId(eventId);
        // 2. Transform Page<EventEntity> -> Page<EventResponseDTO>
        return result.stream().map(entity -> new EventSeatResponse(
                entity.getEventSeatId(),
                entity.getEventSeatType(),
                entity.getPrice(),
                entity.getTotalSeat() - entity.getBookedCount()
        )).toList();
    }

    @Override
    public List<EventResponse> getEvents(String eventTitle, String eventType, String eventDate, Pageable pageable) {

        if (eventDate != null){
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate.parse(eventDate, formatter);
            } catch (DateTimeParseException e) {
                throw new BadRequestException("Date is logically invalid (e.g. Feb 31st)");
            }
        }

        eventTitle = (eventTitle == null || eventTitle.isBlank()) ? null : eventTitle;
        eventType = (eventType == null || eventType.isBlank()) ? null : eventType;
        Page<EventEntity> result = eventRepository.findEvents(eventTitle, eventType, eventDate, pageable);

        List<EventResponse> eventResponseList = result.stream().map(
                eventEntity -> new EventResponse(
                        eventEntity.getEventId(),
                        eventEntity.getEventTitle(),
                        eventEntity.getEventType(),
                        eventEntity.getEventVenue(),
                        eventEntity.getStartDate(),
                        eventEntity.getEndDate()
                )
        ).toList();

        return eventResponseList;
    }

}
