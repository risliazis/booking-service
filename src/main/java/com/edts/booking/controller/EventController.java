package com.edts.booking.controller;

import com.edts.booking.model.dto.request.AddNewEvent;
import com.edts.booking.model.dto.request.AddNewEventSeat;
import com.edts.booking.model.dto.response.EventResponse;
import com.edts.booking.model.dto.response.EventSeatResponse;
import com.edts.booking.model.entity.EventEntity;
import com.edts.booking.model.entity.EventSeatEntity;
import com.edts.booking.service.EventService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@Valid @RequestBody AddNewEvent request){
        log.info("add new event with request: {}", request);
        eventService.add(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{eventId}/seat")
    public ResponseEntity<?> createSeat(@PathVariable Long eventId, @Valid @RequestBody AddNewEventSeat request){
        log.info("add new event seat with request: {}", request);
        eventService.addSeat(eventId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<?> getEvents(@RequestParam(name = "event_title", required = false) String eventTitle,
                                       @RequestParam(name = "event_type", required = false) String eventType,
                                       @RequestParam(name = "event_date", required = false) String eventDate,
                                       Pageable pageable){
        List<EventResponse> result = eventService.getEvents(eventTitle, eventType, eventDate, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{eventId}/seat")
    public ResponseEntity<?> getEventSeat(@PathVariable Long eventId) {
        List<EventSeatResponse> result = eventService.getEventSeatByEventId(eventId);
        return ResponseEntity.ok(result);
    }

}
