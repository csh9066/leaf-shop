package com.leaf.shop.dto.prodcut;

import lombok.Data;

@Data
public class CreateProductRequest {
    private String name;
    private int price;
    private String description;
    private String detail;
    private int stock;
    private long brandId;
}
