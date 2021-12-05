package com.leaf.shop.module.order.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PayCompleteOrderProduct {
    @NotNull
    private long productId;
    @NotNull
    private int count;
}
