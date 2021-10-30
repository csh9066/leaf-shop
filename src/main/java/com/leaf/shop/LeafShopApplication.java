package com.leaf.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LeafShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeafShopApplication.class, args);
    }

}
