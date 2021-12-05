package com.leaf.shop.module.cart.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartItemByBrandDto {
    private Long brandId;
    private String brandName;
    private List<CartItemDto> items = new ArrayList<>();

    public CartItemByBrandDto(Long brandId, String brandName) {
        this.brandId = brandId;
        this.brandName = brandName;
    }

    public void addItem(CartItemDto item) {
        items.add(item);
    }
}
