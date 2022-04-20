package com.leaf.shop.domain.order;

import com.leaf.shop.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "orders")
public class Order {
    @Id
    private String orderNo;
    private OrderPayment payment;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User orderer;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    private OrderAddress address;
    @CreatedDate
    protected LocalDateTime createdAt;
    @LastModifiedDate
    protected LocalDateTime updatedAt;

    @Builder
    private Order(String orderNo, User orderer, List<OrderItem> orderItems, OrderAddress address, OrderPayment payment) {
        this.orderNo = orderNo;
        this.orderer = orderer;
        orderItems.forEach(i -> this.addOrderItem(i));
        this.address = address;
        this.payment = payment;

    }

    private void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void cancelAll() {
        this.orderItems.forEach(
                (oi) -> oi.cancel()
        );
    }
}
