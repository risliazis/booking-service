package com.edts.booking.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AddNewEvent(
        @NotBlank(message = "event title cannot empty")
        String eventTitle,
        String eventVenue,
        @NotBlank(message = "event type cannot empty")
        String eventType,
        @NotNull
        OffsetDateTime startDate,
        @NotNull
        OffsetDateTime endDate
        ) { }
