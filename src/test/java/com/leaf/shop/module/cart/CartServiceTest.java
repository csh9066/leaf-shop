package com.leaf.shop.module.cart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartServiceTest {

    @Autowired
    CartService cartService;
    @Autowired
    CartRepository cartRepository;

    @DisplayName("카트 생성")
    @Nested
    class createCart {

        @Transactional
        @Test
        public void 성공() {
            //given
            long userId = 1L;
            //when
            long cartId  = cartService.createCart(userId);
            //then
            Cart findCart = cartRepository.findById(cartId).get();
            assertEquals(cartId, findCart.getId());
        }

        @Transactional
        @Test
        public void 실패시_AlreadyCartProductExistException_예외발생() {
            //given
            long userId = 1L;
            //when

            //then
            assertThrows(AlreadyCartProductExistException.class, () -> {
                cartService.createCart(userId);
                cartService.createCart(userId);
            });
        }
    }

    @Test
    void findCartByUserId() {
    }

    @Test
    void addCartItem() {
    }
}