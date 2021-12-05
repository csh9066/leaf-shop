package com.leaf.shop.module.product.dto;

import com.leaf.shop.module.brand.Brand;
import com.leaf.shop.module.brand.dto.BrandDto;
import com.leaf.shop.module.product.Product;
import lombok.Data;

@Data
public class ProductDto {
    private long id;
    private String name;
    private int price;
    private String description;
    private String detail;
    private int stock;
    private BrandDto brand;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.detail = product.getDetail();
        this.stock = product.getStock();
        Brand brand = product.getBrand();
        this.brand = BrandDto.builder()
                .id(brand.getId())
                .description(brand.getDescription())
                .name(brand.getName())
                .build();
    }
}
