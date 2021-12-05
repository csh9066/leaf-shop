package com.leaf.shop.module.order.dto;

import com.leaf.shop.module.order.OrderItem;
import com.leaf.shop.module.order.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderItemDto {
    private Long id;
    private String orderNo;
    private OrderStatus orderStatus;
    private String brandName;
    private Long brandId;
    private Long productId;
    private String productName;
    private int count;
    private int amount;

    public OrderItemDto(String orderNo, OrderItem orderItem) {
        this.id = orderItem.getId();
        this.orderNo = orderNo;
        this.orderStatus = orderItem.getOrderStatus();
        this.brandName = orderItem.getProduct().getBrand().getName();
        this.brandId = orderItem.getProduct().getBrand().getId();
        this.productId = orderItem.getProduct().getId();
        this.productName = orderItem.getProduct().getName();
        this.count = orderItem.getCount();
        this.amount = orderItem.getAmount();
    }
}
