package com.leaf.shop.web.controller;

import com.leaf.shop.service.BrandService;
import com.leaf.shop.dto.BrandDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/brands")
@RestController
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public List<BrandDto> findBrands() {
        return brandService.findAll();
    }
}
