package com.edts.booking.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AddNewCustomer(
        @NotBlank(message = "name cannot empty")
        String name,
        String email,
        @NotBlank(message = "mobile no cannot empty")
        String mobileNo
) { }
