package com.bnp.bookstore.service;

import com.bnp.bookstore.Utility;
import com.bnp.bookstore.dto.CartDTO;
import com.bnp.bookstore.entity.CartItem;
import com.bnp.bookstore.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    private final CartRepository cartRepository;

    public CartItemService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartDTO addCartItem(CartDTO cartDTO) {
        CartItem cartItem = Utility.convertObject(cartDTO, CartItem.class);
        cartItem.setActive(true);
        cartRepository.save(cartItem);
        return cartDTO;
    }

    public void removeCartItem(CartItem cartItem) {
        cartRepository.delete(cartItem);
    }

    public void updateCartItem(CartItem existingCartItem, CartDTO updatedCartItem) {
        existingCartItem.setQuantity(updatedCartItem.getQuantity());
        cartRepository.save(existingCartItem);
    }

    public CartItem getCartItem(Long userId, Long bookId) {
        return cartRepository.findByUserIdAndBookIdAndIsActive(userId, bookId, true);
    }
}
