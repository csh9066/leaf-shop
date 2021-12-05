package com.leaf.shop.module.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByUserId(long userId);
    List<Address> findAllByUserIdOrderByIsDefaultAddressDesc(long userId);
}
