package com.leaf.shop.module.cart.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ChangeCartItemCountDto {
    @Positive
    @NotNull
    private int count;
}
