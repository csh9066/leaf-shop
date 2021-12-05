package com.leaf.shop.module.address;

import com.leaf.shop.module.common.converter.BooleanToYNConverter;
import com.leaf.shop.module.address.dto.AddressDto;
import com.leaf.shop.module.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String receiver;
    private String address;
    private String extraAddress;
    private String mainPhone1;
    private String mainPhone2;
    private String mainPhone3;
    private String subPhone1;
    private String subPhone2;
    private String subPhone3;
    private int zipCode;
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean isDefaultAddress;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Address(AddressDto addressDto, User user) {
        this.receiver = addressDto.getReceiver();
        this.address = addressDto.getAddress();
        this.extraAddress = addressDto.getExtraAddress();
        this.mainPhone1 = addressDto.getMainPhone1();
        this.mainPhone2 = addressDto.getMainPhone2();
        this.mainPhone3 = addressDto.getMainPhone3();
        this.subPhone1 = addressDto.getSubPhone1();
        this.subPhone2 = addressDto.getSubPhone2();
        this.subPhone3 = addressDto.getSubPhone3();
        this.zipCode = addressDto.getZipCode();
        this.isDefaultAddress = addressDto.getIsDefaultAddress();
        this.user = user;
    }

    public void updateAddress(AddressDto addressDto) {
        this.receiver = addressDto.getReceiver();
        this.address = addressDto.getAddress();
        this.extraAddress = addressDto.getExtraAddress();
        this.mainPhone1 = addressDto.getMainPhone1();
        this.mainPhone2 = addressDto.getMainPhone2();
        this.mainPhone3 = addressDto.getMainPhone3();
        this.subPhone1 = addressDto.getSubPhone1();
        this.subPhone2 = addressDto.getSubPhone2();
        this.subPhone3 = addressDto.getSubPhone3();
        this.zipCode = addressDto.getZipCode();
        this.isDefaultAddress = addressDto.getIsDefaultAddress();
    }

    public void setDefaultAddress(Boolean defaultAddress) {
        isDefaultAddress = defaultAddress;
    }
}
