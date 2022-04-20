package com.leaf.shop.dto.cart;

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
