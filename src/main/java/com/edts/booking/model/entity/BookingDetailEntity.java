package com.edts.booking.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "BOOKING_DETAIL")
public class BookingDetailEntity extends AuditTrailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOKING_DETAIL_ID")
    private Long bookingDetailId;

    @ManyToOne
    @JoinColumn(name = "BOOKING_ID", nullable = false)
    @Setter
    private BookingEntity booking;

    @ManyToOne
    @JoinColumn(name = "EVENT_SEAT_ID", nullable = false)
    @Setter
    private EventSeatEntity eventSeatEntity;

    @Column(name = "PRICE", nullable = false)
    @Setter
    private BigDecimal price;

    @Column(name = "QUANTITY", nullable = false)
    @Setter
    private Integer qty;
}
