package com.leaf.shop.service;

import com.leaf.shop.domain.order.Order;
import com.leaf.shop.exception.ResourceNotFoundException;
import com.leaf.shop.repository.OrderQueryRepository;
import com.leaf.shop.dto.order.OrderDto;
import com.leaf.shop.dto.order.OrderItemDto;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class OrderQueryService {
    private final OrderQueryRepository orderQueryRepository;
    private final IamportApiService iamportApiService;

    public OrderDto findOrder(String orderNo) {
        Order order = orderQueryRepository.findOrderByOrderNo(orderNo).orElseThrow(
                () -> new ResourceNotFoundException("order", "orderNo", orderNo)
        );
        List<OrderItemDto> orderItemDtos = order.getOrderItems().stream()
                .map(
                        (oi) -> new OrderItemDto(orderNo, oi)
                ).collect(Collectors.toList());

        Payment payRes = iamportApiService.getPaymentData(order.getPayment().getImp_uid()).getResponse();
        return OrderDto.builder()
                .orderNo(orderNo)
                .orderDate(order.getCreatedAt())
                .address(order.getAddress())
                .items(orderItemDtos)
                .payment(payRes)
                .build();
    }
}
