package com.edts.booking.repositories;

import com.edts.booking.model.entity.BookingDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetailEntity, Long> {
    BookingDetailEntity findByBooking_BookingId(Long bookingId);
}
