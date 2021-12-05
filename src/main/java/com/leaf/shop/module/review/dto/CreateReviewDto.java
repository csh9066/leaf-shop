package com.leaf.shop.module.review.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateReviewDto {
    @NotEmpty
    private String content;
    private Long orderItemId;
}
