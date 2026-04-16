package com.edts.booking.service;

import com.edts.booking.model.dto.request.AddNewEvent;
import com.edts.booking.model.dto.request.AddNewEventSeat;
import com.edts.booking.model.dto.response.EventResponse;
import com.edts.booking.model.dto.response.EventSeatResponse;
import com.edts.booking.model.entity.EventSeatEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {
    void add(AddNewEvent request);
    void addSeat(Long eventId, AddNewEventSeat request);
    int bookSeat(Long eventSeatId, int qty);
    EventSeatEntity getEventSeat(Long eventSeatId);
    List<EventSeatResponse> getEventSeatByEventId(Long eventId);
    List<EventResponse> getEvents(String eventTitle, String eventType, String eventDate, Pageable pageable);

}
