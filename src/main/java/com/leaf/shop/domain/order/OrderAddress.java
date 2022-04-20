package com.leaf.shop.domain.order;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class OrderAddress {
    @NotNull
    private String receiver;
    @NotNull
    private String address;
    @NotNull
    private String extraAddress;
    @NotNull
    private int zipCode;
    @NotNull
    private String phone1;
    @NotNull
    private String phone2;
    @NotNull
    private String phone3;
}
