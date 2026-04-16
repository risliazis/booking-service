package com.edts.booking.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "EVENT_SEAT")
public class EventSeatEntity extends AuditTrailEntity{

    @Id
    @Column(name = "EVENT_SEAT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventSeatId;

    @ManyToOne
    @JoinColumn(name = "EVENT_ID", nullable = false)
    @Setter
    private EventEntity event;

    @Column(name = "EVENT_SEAT_TYPE")
    @Setter
    private String eventSeatType;

    @Column(name = "TOTAL_SEAT", nullable = false)
    @Setter
    private Integer totalSeat;

    @Column(name = "BOOKED_COUNT", nullable = false)
    @Setter
    private Integer bookedCount;

    @Column(name = "PRICE", nullable = false)
    @Setter
    private BigDecimal price;
}
