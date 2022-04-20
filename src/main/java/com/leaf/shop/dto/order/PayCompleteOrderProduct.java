package com.leaf.shop.dto.order;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PayCompleteOrderProduct {
    @NotNull
    private long productId;
    @NotNull
    private int count;
}
