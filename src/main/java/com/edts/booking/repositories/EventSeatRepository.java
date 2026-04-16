package com.edts.booking.repositories;

import com.edts.booking.model.entity.EventSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventSeatRepository extends JpaRepository<EventSeatEntity, Long> {
    List<EventSeatEntity> findByEvent_EventId(Long eventEventId);

    @Modifying @Query("""
    UPDATE EventSeatEntity es SET es.bookedCount = es.bookedCount + :qty
    WHERE es.eventSeatId = :id AND es.bookedCount + :qty <= es.totalSeat
    """)
    int bookSeat(@Param("id") Long id, @Param("qty") int qty);
}
