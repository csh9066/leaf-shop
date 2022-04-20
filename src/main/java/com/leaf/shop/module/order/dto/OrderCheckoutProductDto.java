package com.leaf.shop.module.order.dto;

import com.leaf.shop.domain.cart.CartItem;
import lombok.Data;

@Data
public class OrderCheckoutProductDto {
    private long productId;
    private String productName;
    private int count;
    private int purchasePrice;
    private String brandName;
    private long brandId;

    public OrderCheckoutProductDto(CartItem cartItem) {
        this.productId = cartItem.getProduct().getId();
        this.productName = cartItem.getProduct().getName();
        this.count = cartItem.getCount();
        this.purchasePrice = cartItem.calPurchasePrice();
        this.brandName = cartItem.getProduct().getBrand().getName();
        this.brandId = cartItem.getProduct().getBrand().getId();
    }
}
