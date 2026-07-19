package com.bnp.bookstore.repository;

import com.bnp.bookstore.entity.Book;
import com.bnp.bookstore.entity.CartItem;
import com.bnp.bookstore.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {

//    List<CartItem> findByUser(User user);
//
//    Optional<CartItem> findByUserAndBook(User user, Book book);
//
//    void deleteByUser(User user);

    @Query("SELECT c FROM CartItem c WHERE c.isActive = true AND c.userId = :userId")
    List<CartItem> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE CartItem c SET c.isActive = false WHERE c.userId = :userId")
    void clearAll(@Param("userId") Long userId);

    CartItem findByUserIdAndBookIdAndIsActive(Long userId, Long bookId, Boolean isActive);
}
