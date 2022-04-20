package com.leaf.shop.dto.order;

import com.leaf.shop.dto.AddressDto;
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
