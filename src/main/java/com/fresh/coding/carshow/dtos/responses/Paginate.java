package com.fresh.coding.carshow.dtos.responses;

public record Paginate<T>(
        T items,
        Integer currentPage,
        Integer totalPages,
        Long totalItems
) {
}
