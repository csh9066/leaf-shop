package com.leaf.shop.dto.cart;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AddCartItemDto {
    @NotNull
    @Min(1)
    @Max(999)
    private int count;
    @NotNull
    private long productId;
}
