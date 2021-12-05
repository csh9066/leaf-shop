package com.leaf.shop.module.order;

import com.leaf.shop.module.address.Address;
import com.leaf.shop.module.address.AddressRepository;
import com.leaf.shop.module.address.AddressService;
import com.leaf.shop.module.address.dto.AddressDto;
import com.leaf.shop.module.cart.Cart;
import com.leaf.shop.module.cart.CartRepository;
import com.leaf.shop.module.cart.CartService;
import com.leaf.shop.module.order.dto.PayCompleteOrderForm;
import com.leaf.shop.module.order.dto.OrderCheckoutDto;
import com.leaf.shop.module.order.dto.OrderCheckoutProductDto;
import com.leaf.shop.module.payment.IamportApiService;
import com.leaf.shop.module.product.Product;
import com.leaf.shop.module.product.ProductRepository;
import com.leaf.shop.module.user.User;
import com.leaf.shop.module.user.UserService;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class OrderService {
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final UserService userService;
    private final AddressRepository addressRepository;
    private final AddressService addressService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final IamportApiService iamportApiService;

    public OrderCheckoutDto findOrderCheckoutDto(long userId) {
        AddressDto addressDto = null;
        List<Address> addresses = addressRepository.findAllByUserIdOrderByIsDefaultAddressDesc(userId);
        if (!addresses.isEmpty()) {
            addressDto = new AddressDto(addresses.get(0));
        }

        Cart cart = cartRepository.findCartFetchJoinByUserIdAndItemsCheckedTrue(userId);
        List<OrderCheckoutProductDto> orderCheckoutProductDtos = cart.getItems().stream()
                .map(OrderCheckoutProductDto::new)
                .collect(Collectors.toList());

        return new OrderCheckoutDto(addressDto,
                orderCheckoutProductDtos,
                0,
                cart.calTotalPItemPrice(),
                cart.calTotalPItemPrice()
                );
    }

//    주문 번호 : yymmdd + System.currentTimeMillis()의 뒤에 8자리 숫자
    public String generateOrderNo() {
        Long curTime = System.currentTimeMillis();
        String curTimeToString = curTime.toString();
        curTimeToString = curTimeToString.substring(curTimeToString.length() - 8);
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        return now+curTimeToString;
    }

    @Transactional
    public void cancelAll(String orderNo) {
        Order order = orderRepository.findById(orderNo).get();
        order.cancelAll();
    }

    @Transactional
    public void orderPaymentComplete(long userId, PayCompleteOrderForm paymentCompleteOrderForm) {
        User orderer = userService.findUserById(userId);

        if (paymentCompleteOrderForm.getAddress().getId() == null){
             addressService.createAddress(userId, paymentCompleteOrderForm.getAddress());
        }

        OrderAddress orderAddress = OrderAddress.builder()
                .address(paymentCompleteOrderForm.getAddress().getAddress())
                .extraAddress(paymentCompleteOrderForm.getAddress().getExtraAddress())
                .phone1(paymentCompleteOrderForm.getAddress().getMainPhone1())
                .phone2(paymentCompleteOrderForm.getAddress().getMainPhone2())
                .phone3(paymentCompleteOrderForm.getAddress().getMainPhone3())
                .receiver(paymentCompleteOrderForm.getAddress().getReceiver())
                .zipCode(paymentCompleteOrderForm.getAddress().getZipCode())
                .build();

        List<OrderItem> orderItems = paymentCompleteOrderForm.getProducts()
                .stream()
                .map(p -> {
                    Product product = productRepository.findById(p.getProductId()).orElseThrow(
                            () -> new IllegalStateException("주문 실패")
                    );
                    int amount = product.getPrice() * p.getCount();
                    return OrderItem.createPaymentCompOrderItem(product, p.getCount(), amount);
                })
                .collect(Collectors.toList());

        Payment papRes = iamportApiService.getPaymentData(paymentCompleteOrderForm.getImpUid()).getResponse();

        Order order = Order.builder()
                .orderNo(paymentCompleteOrderForm.getOrderNo())
                .orderer(orderer)
                .address(orderAddress)
                .orderItems(orderItems)
                .payment(new OrderPayment(papRes.getImpUid(), papRes.getAmount().longValue()))
                .build();

        orderRepository.save(order);

        List<Long> productIds = paymentCompleteOrderForm.getProducts()
                .stream()
                .map(p -> p.getProductId())
                .collect(Collectors.toList());

        cartService.emptyCartItems(userId, productIds);
    }
}
