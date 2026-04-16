package com.edts.booking.controller;

import com.edts.booking.model.dto.request.AddNewBooking;
import com.edts.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody AddNewBooking request){
        log.info("request booking with payload: {}", request);
        AddNewBooking newRequest = request.normalizeEventSeat();
        bookingService.add(newRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId){
        log.info("cancel booking for booking ID: {}", bookingId);
        bookingService.cancel(bookingId);
        return ResponseEntity.noContent().build();
    }

}
