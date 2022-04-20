package com.leaf.shop.service;

import com.leaf.shop.domain.brand.BrandRepository;
import com.leaf.shop.module.brand.dto.BrandDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public List<BrandDto> findAll() {
        return brandRepository.findAll().stream()
                .map(BrandDto::new)
                .collect(Collectors.toList());
    }
}
