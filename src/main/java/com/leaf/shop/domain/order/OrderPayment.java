package com.leaf.shop.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class OrderPayment {
    private String imp_uid;
    private Long amount;
//    private Long deliveryFee;
//    private String payMethod;


    public OrderPayment(String imp_uid, Long amount) {
        this.imp_uid = imp_uid;
        this.amount = amount;
    }
}
