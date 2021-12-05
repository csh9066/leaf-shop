package com.leaf.shop.module.review.dto;

import com.leaf.shop.module.review.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ReviewDto {
    private Long id;
    private String content;
    private Long productId;
    private String productName;
    private Long brandId;
    private String brandName;
    private LocalDateTime updatedAt;
    private String author;

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.productId = review.getProduct().getId();
        this.productName = review.getProduct().getName();
        this.brandId = review.getProduct().getBrand().getId();
        this.brandName = review.getProduct().getBrand().getName();
        this.author = review.getUser().getNickname();
        this.updatedAt = review.getUpdatedAt();
    }
}
