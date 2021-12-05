package com.leaf.shop.module.cart;

import com.leaf.shop.module.common.converter.BooleanToYNConverter;
import com.leaf.shop.module.common.domain.BaseTimeEntity;
import com.leaf.shop.module.product.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CartItem extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_ID")
    private Cart cart;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    private int count;  
    @Convert(converter = BooleanToYNConverter.class)
    private boolean checked;

    @Builder
    private CartItem(Cart cart, Product product, int count) {
        this.cart = cart;
        this.product = product;
        this.count = count;
        this.checked = true;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void changeCount(int count) {
        if (product.getStock() < count) {
            throw new AlreadyCartProductExistException("상품 재고가 부족합니다.");
        }
        this.count = count;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int calPurchasePrice() {
        return product.getPrice() * count;
    }

}
