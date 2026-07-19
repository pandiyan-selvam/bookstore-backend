package com.bnp.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        T data,
        Object errors
) {
    // Utility for successful responses with data
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, null);
    }

    // Utility for error responses
    public static <T> ApiResponse<T> error(String message, Object errors) {
        return new ApiResponse<>(null, errors);
    }
}

