package com.leaf.shop.module.product;

import com.leaf.shop.module.product.dto.ProductDto;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.leaf.shop.module.brand.QBrand.*;
import static com.leaf.shop.module.product.QProduct.*;

@RequiredArgsConstructor
@Repository
public class ProductQueryRepository {
    private final JPQLQueryFactory queryFactory;

    public List<ProductDto> searchProducts(Pageable pageable) {
        List<Product> products = queryFactory
                .selectFrom(product)
                .join(product.brand, brand).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                                .fetch();
        return products.stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }
}
