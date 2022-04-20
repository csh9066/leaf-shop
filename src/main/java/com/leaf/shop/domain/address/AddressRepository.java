package com.leaf.shop.domain.address;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByUserId(long userId);
    List<Address> findAllByUserIdOrderByIsDefaultAddressDesc(long userId);
}
