package com.edts.booking.service;

import com.edts.booking.model.dto.request.AddNewEvent;
import com.edts.booking.model.dto.request.AddNewEventSeat;
import com.edts.booking.model.entity.EventEntity;
import com.edts.booking.model.entity.EventSeatEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    void add(AddNewEvent request);
    void addSeat(Long eventId, AddNewEventSeat request);
    EventSeatEntity getEventSeat(Long eventSeatId);
    Page<EventEntity> getEvents(String eventTitle, String eventType, Pageable pageable);

}
