package com.fresh.coding.carshow.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserSummarized(
        Long id,
        String email,
        String name,
        String password
) {
}
