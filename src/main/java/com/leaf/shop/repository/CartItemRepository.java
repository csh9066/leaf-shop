package com.leaf.shop.repository;

import com.leaf.shop.domain.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByProductId(Long productId);
}
