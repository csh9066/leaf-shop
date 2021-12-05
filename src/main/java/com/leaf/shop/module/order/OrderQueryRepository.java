package com.leaf.shop.module.order;

import com.leaf.shop.module.address.QAddress;
import com.leaf.shop.module.brand.QBrand;
import com.leaf.shop.module.order.dto.OrderDto;
import com.leaf.shop.module.order.dto.OrderItemDto;
import com.leaf.shop.module.product.QProduct;
import com.leaf.shop.module.user.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.leaf.shop.module.address.QAddress.*;
import static com.leaf.shop.module.brand.QBrand.*;
import static com.leaf.shop.module.order.QOrder.*;
import static com.leaf.shop.module.order.QOrderItem.*;
import static com.leaf.shop.module.product.QProduct.product;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {
    private final JPQLQueryFactory queryFactory;

    public Optional<Order> findOrderByOrderNo(String orderNo) {
        Order result =  queryFactory
                .selectFrom(order)
                .join(order.orderItems, orderItem).fetchJoin()
                .join(orderItem.product, product).fetchJoin()
                .join(product.brand, brand).fetchJoin()
                .where(
                        order.orderNo.eq(orderNo)
                )
                .fetchOne();
        return Optional.ofNullable(result);
    }

    public List<OrderItemDto> searchOrderItem(Long userId, Boolean isWriteReview) {
        return queryFactory
                .select(Projections.bean(OrderItemDto.class
                        ,orderItem.id
                        ,order.orderNo
                        , orderItem.count
                        , orderItem.amount
                        , orderItem.orderStatus
                        , product.id.as("productId")
                        , product.name.as("productName")
                        , brand.id.as("brandId"), brand.name.as("brandName")))
                .from(orderItem)
                .join(orderItem.order, order)
                .join(orderItem.product, product)
                .join(product.brand, brand)
                .where(
                        order.orderer.id.eq(userId)
                        .and(
                                isWriteReviewEq(isWriteReview)
                        )
                )
                .orderBy(
                        order.createdAt.desc()
                )
                .fetch();
    }

    private BooleanExpression isWriteReviewEq(Boolean isWriteReview) {
        return isWriteReview == null ? null : orderItem.isWriteReview.eq(isWriteReview);
    }
}
