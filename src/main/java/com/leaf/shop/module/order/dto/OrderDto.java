package com.leaf.shop.module.order.dto;


import com.leaf.shop.module.order.OrderAddress;
import com.siot.IamportRestClient.response.Payment;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class OrderDto {
    private String orderNo;
    private LocalDateTime orderDate;
    private Payment payment;
    private OrderAddress address;
    private List<OrderItemDto> items;

    @Builder
    public OrderDto(String orderNo, LocalDateTime orderDate, Payment payment, OrderAddress address, List<OrderItemDto> items) {
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.payment = payment;
        this.address = address;
        this.items = items;
    }
}
