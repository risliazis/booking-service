package com.edts.booking.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AddNewEventSeat(
        String eventSeatType,
        @Min(1) Integer totalSeat,
        @NotNull @Min(0)
        BigDecimal price
) { }
