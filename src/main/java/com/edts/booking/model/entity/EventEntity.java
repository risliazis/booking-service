package com.edts.booking.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Entity
@Table(name = "EVENT")
public class EventEntity extends AuditTrailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID")
    private Long eventId;

    @Column(name = "EVENT_TITLE", nullable = false, length = 50)
    @Setter
    private String eventTitle;

    @Column(name = "EVENT_VENUE", length = 100)
    @Setter
    private String eventVenue;

    @Column(name = "EVENT_TYPE", length = 25)
    @Setter
    private String eventType;

    @Column(name = "START_DATE", nullable = false)
    @Setter
    private Instant startDate;

    @Column(name = "END_DATE", nullable = false)
    @Setter
    private Instant endDate;
}
