package com.leaf.shop.service;

import com.leaf.shop.domain.address.Address;
import com.leaf.shop.domain.address.AddressRepository;
import com.leaf.shop.dto.AddressDto;
import com.leaf.shop.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class AddressService {
    private final UserService userService;
    private final AddressRepository addressRepository;

    public long createAddress(long userId, AddressDto addressDto) {
        User user = userService.findUserById(userId);
        if (addressDto.getIsDefaultAddress()) {
            List<Address> addresses = addressRepository.findAllByUserId(userId);
            addresses.forEach(a -> a.setDefaultAddress(false));
        }
        Address address = new Address(addressDto, user);
        addressRepository.save(address);
        return address.getId();
    }

    public List<AddressDto> findMyAddresses(long userId) {
        List<Address> addresses = addressRepository.findAllByUserIdOrderByIsDefaultAddressDesc(userId);
        return addresses.stream().map(AddressDto::new).collect(Collectors.toList());
    }

    public void updateAddress(long addressId, AddressDto addressDto) {
        Address address = addressRepository.getById(addressId);
        address.updateAddress(addressDto);
    }

    public void deleteAddress(long addressId) {
        addressRepository.deleteById(addressId);
    }
}
