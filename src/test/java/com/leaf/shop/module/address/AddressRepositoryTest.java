package com.leaf.shop.module.address;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class AddressRepositoryTest {
    @Autowired AddressRepository addressRepository;
    @Test
    void findAddressByUserIdAndIsDefaultAddressExists() {

    }
}