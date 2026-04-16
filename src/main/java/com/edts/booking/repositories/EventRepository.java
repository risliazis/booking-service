package com.edts.booking.repositories;

import com.edts.booking.model.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Query(value = """
    SELECT * FROM EVENT e 
    WHERE (:eventTitle IS NULL OR e.event_title ILIKE CONCAT('%', :eventTitle, '%'))
      AND (:eventType IS NULL OR e.event_type = :eventType)
      AND (:eventDate IS NULL OR e.end_date <= TO_TIMESTAMP(:eventDate, 'DD-MM-YYYY'))
    """,
            countQuery = """
    SELECT count(*) FROM event_table e 
    WHERE (:eventTitle IS NULL OR e.event_title ILIKE CONCAT('%', :eventTitle, '%'))
      AND (:eventType IS NULL OR e.event_type = :eventType)
      AND (:eventDate IS NULL OR e.end_date <= TO_TIMESTAMP(:eventDate, 'DD-MM-YYYY'))
    """,
            nativeQuery = true)
    Page<EventEntity> findEvents(@Param("eventTitle") String eventTitle,
                                 @Param("eventType") String eventType,
                                 @Param("eventDate") String eventDate,
                                 Pageable pageable
    );

//    @Query("SELECT e FROM EventEntity e WHERE :eventTitle IS NULL OR LOWER(e.eventTitle) LIKE :eventTitle")
//    Page<EventEntity> findEvents(@Param("eventTitle") String eventTitle,
//                                 @Param("eventType") String eventType,
//                                 @Param("eventDate") String eventDate,
//                                 Pageable pageable
//    );

}
