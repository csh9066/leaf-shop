package com.leaf.shop.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.siot.IamportRestClient.IamportClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class AppConfig {
    @Value("${iamport.api-key}")
    private String iamPortApiKey;
    @Value("${iamport.api-secret}")
    private String iamPortApiSecret;

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(iamPortApiKey, iamPortApiSecret);
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
