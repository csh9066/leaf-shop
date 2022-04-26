package com.leaf.shop.domain.product;

import com.leaf.shop.domain.brand.Brand;
import com.leaf.shop.domain.BaseTimeEntity;
import com.leaf.shop.domain.category.Category;
import com.leaf.shop.domain.review.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;
    private String description;
    @Column(columnDefinition = "TEXT")
    private String detail;
    private int stock;
    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images = new ArrayList<>();

    @Builder
    public Product(String name, int price, String description, String detail, int stock, Brand brand) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.detail = detail;
        this.stock = stock;
        this.brand = brand;
    }

    public void writeReview(Review review) {
        this.reviews.add(review);
        review.setProduct(this);
    }
}
