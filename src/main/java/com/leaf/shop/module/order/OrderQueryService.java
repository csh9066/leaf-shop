package com.leaf.shop.module.order;

import com.leaf.shop.domain.order.Order;
import com.leaf.shop.module.common.exception.ResourceNotFoundException;
import com.leaf.shop.module.order.dto.OrderDto;
import com.leaf.shop.module.order.dto.OrderItemDto;
import com.leaf.shop.module.payment.IamportApiService;
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
