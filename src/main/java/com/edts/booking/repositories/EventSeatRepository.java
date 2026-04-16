package com.edts.booking.repositories;

import com.edts.booking.model.entity.EventSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventSeatRepository extends JpaRepository<EventSeatEntity, Long> {
}
