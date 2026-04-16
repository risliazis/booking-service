package com.edts.booking.service;

import com.edts.booking.model.dto.request.AddNewBooking;

public interface BookingService {
    void add(AddNewBooking request);
    void cancel(Long bookingId);
}
