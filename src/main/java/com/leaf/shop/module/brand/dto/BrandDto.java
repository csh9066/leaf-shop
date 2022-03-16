package com.leaf.shop.module.brand.dto;

import com.leaf.shop.module.brand.Brand;
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

    public BrandDto(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.description = brand.getDescription();
    }
}
