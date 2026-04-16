package com.edts.booking.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity extends AuditTrailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "CUSTOMER_NAME", nullable = false, length = 100)
    @Setter
    private String customerName;

    @Column(name = "CUSTOMER_EMAIL", length = 100)
    @Setter
    private String customerEmail;

    @Column(name = "CUSTOMER_MOBILE_NO", nullable = false, length = 15, unique = true)
    @Setter
    private String customerMobileNo;
}
