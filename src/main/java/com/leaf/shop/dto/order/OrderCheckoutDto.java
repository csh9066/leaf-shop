package com.leaf.shop.dto.order;

import com.leaf.shop.dto.AddressDto;
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
