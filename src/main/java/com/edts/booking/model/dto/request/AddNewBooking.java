package com.edts.booking.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AddNewBooking(
        @NotNull(message = "customer ID cannot empty")
        Long customerId,
        @NotEmpty(message = "event seat cannot empty")
        List<BookingEventSeat> eventSeat,
        @NotNull(message = "total amount cannot empty")
        BigDecimal totalAmount
) {
    public AddNewBooking normalizeEventSeat() {

        List<BookingEventSeat> normalizedEventSeat = new ArrayList<>();

        Map<Long, Integer> seatMap = eventSeat.stream()
                .collect(Collectors.groupingBy(
                        BookingEventSeat::eventSeatId,
                        Collectors.summingInt(BookingEventSeat::qty)
                ));

        for (Map.Entry<Long, Integer> entry : seatMap.entrySet()) {
            BookingEventSeat bookingEventSeat = new BookingEventSeat(entry.getKey(), entry.getValue());
            normalizedEventSeat.add(bookingEventSeat);
        }

        return new AddNewBooking(this.customerId, normalizedEventSeat, this.totalAmount);
    }

}
