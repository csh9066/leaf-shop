package com.leaf.shop.module.brand.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class BrandDto {
    private long id;
    private String name;
    private String description;
}
