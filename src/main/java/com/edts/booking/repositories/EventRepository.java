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

    @Query("""
    select e from EventEntity e
    where (:eventTitle IS NULL OR lower(e.eventTitle) like lower(concat('%', :eventTitle, '%')))
""")
    Page<EventEntity> findEvents(@Param("eventTitle") String eventTitle,
                                 @Param("eventType") String eventType,
                                 Pageable pageable);

}
