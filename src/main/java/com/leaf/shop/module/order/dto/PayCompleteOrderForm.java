package com.leaf.shop.module.order.dto;

import com.leaf.shop.module.address.dto.AddressDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PayCompleteOrderForm {
    @NotNull
    private String orderNo;
    @NotNull
    private String impUid;
    @NotNull
    private AddressDto address;
    @NotEmpty
    private List<PayCompleteOrderProduct> products;
}
