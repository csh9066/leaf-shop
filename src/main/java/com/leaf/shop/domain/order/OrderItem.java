package com.leaf.shop.domain.order;

import com.leaf.shop.module.common.domain.BaseTimeEntity;
import com.leaf.shop.domain.product.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderItem extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    private int count;
    private int amount;
    private Boolean isWriteReview;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public static OrderItem createPaymentCompOrderItem(Product product, int count, int amount) {
        return OrderItem.builder()
                .count(count)
                .product(product)
                .orderStatus(OrderStatus.PAY_COMPLETE)
                .amount(amount)
                .isWriteReview(false)
                .build();
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Builder
    private OrderItem(Product product, int count, int amount, OrderStatus orderStatus, Boolean isWriteReview) {
        this.product = product;
        this.count = count;
        this.amount = amount;
        this.orderStatus = orderStatus;
        this.isWriteReview = isWriteReview;
    }

    public void cancel() {
        if (orderStatus != OrderStatus.PAY_PENDING ) {
            return;
        }
        orderStatus = OrderStatus.ORDER_CANCEL;
    }

    public void afterWriteReview() {
        this.isWriteReview = false;
    }
}
