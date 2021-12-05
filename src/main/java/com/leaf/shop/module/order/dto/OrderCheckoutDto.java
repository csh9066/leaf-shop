package com.leaf.shop.module.order.dto;

import com.leaf.shop.module.address.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class OrderCheckoutDto {
    private AddressDto address;
    private List<OrderCheckoutProductDto> products;
    private int deliveryFee;
    private int productsPrice;
    private int paymentPrice;
}
