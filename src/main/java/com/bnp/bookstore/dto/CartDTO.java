package com.bnp.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
    private Long userId;
    private Long bookId;
    private Integer quantity;

    public CartDTO() {
    }

    public CartDTO(Long userId, Long bookId, Integer quantity) {
        this.userId = userId;
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
