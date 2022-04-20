package com.leaf.shop.service;

import com.leaf.shop.domain.product.Product;
import com.leaf.shop.repository.ProductRepository;
import com.leaf.shop.exception.ResourceNotFoundException;
import com.leaf.shop.dto.prodcut.CreateProductRequest;
import com.leaf.shop.dto.prodcut.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(CreateProductRequest createForm) {
                Product product = Product.builder()
                .name(createForm.getName())
                .description(createForm.getDescription())
                .detail(createForm.getDetail())
                .price(createForm.getPrice())
                .stock(createForm.getStock())
                .build();
        productRepository.save(product);
    }

    public Product findProductById(long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("product", "id" ,productId)
        );
        return product;
    }

    public List<ProductDto> findProducts() {
        return productRepository.findAll()
                .stream().map(ProductDto::new)
                .collect(Collectors.toList());
    }
}

