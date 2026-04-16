package com.edts.booking.model.dto.response;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record EventSeatResponse(
        Long id,
        String seatType,
        BigDecimal price,
        Integer availableSeat) {
}
