package com.leaf.shop.module.cart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class CartRepositoryTest {
    @Autowired CartRepository cartRepository;

    @Test
    void findCartFetchJoinByUserId() {
        cartRepository.findCartFetchJoinByUserId(1L);
    }
}