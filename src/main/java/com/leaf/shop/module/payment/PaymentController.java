package com.leaf.shop.module.payment;

import com.leaf.shop.module.order.OrderPayment;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.AccessToken;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/api/payments")
@RestController
public class PaymentController {

    private final IamportClient iamportClient;
    private final IamportApiService iamportApiService;

    @GetMapping("/complete")
    public String completePayment() {
        IamportResponse<AccessToken> res = null;
        String token = null;
        try {
            res = iamportClient.getAuth();
            token = res.getResponse().getToken();

        } catch (IamportResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    @GetMapping("/{uid}")
    public IamportResponse<Payment> getPayment(@PathVariable("uid") String uid) {
       return iamportApiService.getPaymentData(uid);
    }
}
