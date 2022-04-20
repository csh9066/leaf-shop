package com.leaf.shop.web.controller;

import com.leaf.shop.domain.product.Product;
import com.leaf.shop.module.product.ProductQueryRepository;
import com.leaf.shop.module.product.ProductService;
import com.leaf.shop.module.product.dto.CreateProductRequest;
import com.leaf.shop.module.review.dto.CreateReviewDto;
import com.leaf.shop.module.product.dto.ProductDto;
import com.leaf.shop.module.review.ReviewService;
import com.leaf.shop.security.AuthUser;
import com.leaf.shop.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/products")
@RestController 
public class ProductController {

    private final ProductService productService;
    private final ProductQueryRepository productQueryRepository;
    private final ReviewService reviewService;

    @GetMapping("/{productId}")
    public ProductDto findProductById(@PathVariable int productId) {
        Product product = productService.findProductById(productId);
        return new ProductDto(product);
    }

    @GetMapping
    public List<ProductDto> searchProducts(@PageableDefault() Pageable pageable) {
        return productQueryRepository.searchProducts(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createProduct(@RequestBody CreateProductRequest createForm) {
        productService.createProduct(createForm);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reviews")
    public void writeReview(@RequestBody CreateReviewDto createReviewDto, @AuthUser UserPrincipal user) {
        reviewService.writeReview(user.getUserId(), createReviewDto);
    }
}
