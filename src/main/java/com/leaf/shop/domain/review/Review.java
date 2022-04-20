package com.leaf.shop.domain.review;

import com.leaf.shop.domain.BaseTimeEntity;
import com.leaf.shop.domain.product.Product;
import com.leaf.shop.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PRODUCT_REVIEW")
@Entity
public class Review extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    public Review(String content, User user) {
        this.content = content;
        this.user = user;
    }

    public void modify(String content) {
        this.content = content;
    }
}
