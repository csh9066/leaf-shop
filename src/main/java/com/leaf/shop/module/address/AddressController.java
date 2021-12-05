package com.leaf.shop.module.address;

import com.leaf.shop.module.address.dto.AddressDto;
import com.leaf.shop.security.AuthUser;
import com.leaf.shop.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/addresses")
@RestController
public class AddressController {
    private final AddressService addressService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public Long createAddress(@AuthUser UserPrincipal user,
                              @RequestBody @Valid AddressDto addressDto) {
        return addressService.createAddress(user.getUserId(), addressDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{addressId}")
    public void updateAddress(@PathVariable("addressId") long addressId,
                              @RequestBody @Valid AddressDto addressDto) {
        addressService.updateAddress(addressId, addressDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public List<AddressDto> findMyAddresses(@AuthUser UserPrincipal user) {
        return addressService.findMyAddresses(user.getUserId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{addressId}")
    public void deleteAddress(@PathVariable("addressId") long addressId) {
        addressService.deleteAddress(addressId);
    }
}
