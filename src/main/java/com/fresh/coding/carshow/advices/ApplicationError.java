package com.fresh.coding.carshow.advices;


import java.time.LocalDate;

public record ApplicationError<T>(
        T message,
        LocalDate errorDate,
        int status
) {
}
