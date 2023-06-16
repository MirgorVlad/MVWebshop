package com.mvwebshop.cartservice.repository;

import com.mvwebshop.cartservice.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteCartItemByProductId(Long productId);

    List<CartItem> findAllByUserId(Long userId);
}
