package com.leaf.shop.module.cart.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CartItemDto {
    private Long productId;
    private String productName;
    private int price;
    private int count;
    private Long cartItemId;
    private boolean checked;
}
