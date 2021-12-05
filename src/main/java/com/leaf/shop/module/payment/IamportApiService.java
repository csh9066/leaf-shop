package com.leaf.shop.module.payment;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class IamportApiService {
    private final IamportClient iamportClient;

    public String getAccessToken() {
        String result = null;
        try {
            result =  iamportClient.getAuth().getResponse().getToken();
        } catch (IamportResponseException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public IamportResponse<Payment> getPaymentData(String impUid) {
        IamportResponse<Payment> result = null;
        try {
            result = iamportClient.paymentByImpUid(impUid);
        } catch (IamportResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
