package com.edts.booking.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class AuditTrailEntity {
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @Setter
    private String createdBy;
    @Setter
    private String modifiedBy;
}
