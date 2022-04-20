package com.leaf.shop.repository;

import com.leaf.shop.domain.brand.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
