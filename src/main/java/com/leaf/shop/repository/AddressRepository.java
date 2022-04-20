package com.leaf.shop.repository;

import com.leaf.shop.domain.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByUserId(long userId);
    List<Address> findAllByUserIdOrderByIsDefaultAddressDesc(long userId);
}
