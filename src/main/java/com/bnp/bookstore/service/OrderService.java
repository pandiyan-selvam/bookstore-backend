package com.bnp.bookstore.service;

import com.bnp.bookstore.entity.Book;
import com.bnp.bookstore.entity.CartItem;
import com.bnp.bookstore.entity.Order;
import com.bnp.bookstore.entity.OrderItem;
import com.bnp.bookstore.repository.OrderItemRepository;
import com.bnp.bookstore.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;
    private final BookService bookService;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, CartService cartService, BookService bookService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartService = cartService;
        this.bookService = bookService;
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).get();
    }

    @Transactional
    public void placeOrder(Long userId) {

        List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);
        List<Long> bookIds = cartItems.stream().map(CartItem::getBookId).collect(Collectors.toList());
        List<Book> books = bookService.getAllBooks(bookIds);
        List<OrderItem> orderItems = new ArrayList<>();

        Map<Long, BigDecimal> booksPriceMap = books.stream()
                .collect(Collectors.toMap(Book::getId, Book::getPrice));

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(booksPriceMap.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setCreatedAt(LocalDateTime.now());

        order = orderRepository.save(order);

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setBookId(cartItem.getBookId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(booksPriceMap.get(cartItem.getBookId()));
            orderItems.add(orderItem);
        }

        orderItemRepository.saveAll(orderItems);
        cartService.clearCart(userId);
    }
}
