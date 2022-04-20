package com.leaf.shop.dto.review;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateReviewDto {
    @NotEmpty
    private String content;
    private Long orderItemId;
}
