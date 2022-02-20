package com.leaf.shop.module.address.dto;

import com.leaf.shop.module.address.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class AddressDto {
    private Long id;
    @NotEmpty
    private String receiver;
    @NotEmpty
    private String address;
    @NotEmpty
    private String extraAddress;
    @NotEmpty
    private String mainPhone1;
    @NotEmpty
    private String mainPhone2;
    @NotEmpty
    private String mainPhone3;
    private String subPhone1;
    private String subPhone2;
    private String subPhone3;
    @NotNull
    private int zipCode;
    private Boolean isDefaultAddress;

    public AddressDto(Address address) {
        this.id = address.getId();
        this.receiver = address.getReceiver();
        this.address = address.getAddress();
        this.extraAddress = address.getExtraAddress();
        this.mainPhone1 = address.getMainPhone1();
        this.mainPhone2 = address.getMainPhone2();
        this.mainPhone3 = address.getMainPhone3();
        this.subPhone1 = address.getSubPhone1();
        this.subPhone2 = address.getSubPhone2();
        this.subPhone3 = address.getSubPhone3();
        this.zipCode = address.getZipCode();
        this.isDefaultAddress = address.getIsDefaultAddress();
    }
}
