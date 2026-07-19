package com.bnp.bookstore.controller;

import com.bnp.bookstore.dto.CartDTO;
import com.bnp.bookstore.entity.CartItem;
import com.bnp.bookstore.service.CartItemService;
import com.bnp.bookstore.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartItemService cartItemService;
    private final CartService cartService;

    public CartController(CartItemService cartItemService, CartService cartService) {
        this.cartItemService = cartItemService;
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartDTO> addCartItem(@RequestBody CartDTO cartItem) {
        CartDTO addedItem = cartItemService.addCartItem(cartItem);
        return ResponseEntity.ok(addedItem);
    }

    @PatchMapping
    public ResponseEntity<CartDTO> updateCartItem(@RequestBody CartDTO cartItem) {
        CartItem existingItem = cartItemService.getCartItem(cartItem.getUserId(), cartItem.getBookId());
        if (existingItem == null) {
            return ResponseEntity.notFound().build();
        }

        cartItemService.updateCartItem(existingItem, cartItem);
        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeCartItem(@RequestBody CartDTO cartItem) {
        CartItem existingItem = cartItemService.getCartItem(cartItem.getUserId(), cartItem.getBookId());
        if (existingItem == null) {
            return ResponseEntity.notFound().build();
        }
        cartItemService.removeCartItem(existingItem);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long userId) {
        List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }

}
