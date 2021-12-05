package com.leaf.shop.module.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    public List<Review> findByUserId(Long userId);
    public List<Review> findByProductId(Long productId);
}
