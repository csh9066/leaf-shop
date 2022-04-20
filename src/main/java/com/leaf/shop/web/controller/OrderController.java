package com.leaf.shop.web.controller;

import com.leaf.shop.repository.OrderQueryRepository;
import com.leaf.shop.service.OrderQueryService;
import com.leaf.shop.service.OrderService;
import com.leaf.shop.dto.order.OrderDto;
import com.leaf.shop.dto.order.OrderItemDto;
import com.leaf.shop.dto.order.PayCompleteOrderForm;
import com.leaf.shop.dto.order.OrderCheckoutDto;
import com.leaf.shop.security.AuthUser;
import com.leaf.shop.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/order")
@RestController
public class OrderController {
    private final OrderService orderService;
    private final OrderQueryService orderQueryService;
    private final OrderQueryRepository orderQueryRepository;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/gen-orderNo")
    public String generateOrderNo() {
        return orderService.generateOrderNo();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/checkout")
    public OrderCheckoutDto checkout(@AuthUser UserPrincipal user) {
        return orderService.findOrderCheckoutDto(user.getUserId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/pay-complete")
    public void paymentComplete(@AuthUser UserPrincipal user,
                                @RequestBody @Valid PayCompleteOrderForm paymentCompleteOrderForm) {
        orderService.orderPaymentComplete(user.getUserId(), paymentCompleteOrderForm);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{orderNo}")
    public OrderDto findOrder(@PathVariable("orderNo") String orderNo) {
        return orderQueryService.findOrder(orderNo);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public List<OrderItemDto> searchOrderItem(@AuthUser UserPrincipal user
            , @RequestParam(value = "is_write_review", required = false) Boolean isWriteReview) {
        return orderQueryRepository.searchOrderItem(user.getUserId(), isWriteReview);
    }


}
