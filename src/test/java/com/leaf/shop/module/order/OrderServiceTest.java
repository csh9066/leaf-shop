package com.leaf.shop.module.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class OrderServiceTest {
    @Test
    public void generateOrderNo() throws Exception {
        //given
        Long curTime = System.currentTimeMillis();
        String curTimeToString = curTime.toString();
        curTimeToString = curTimeToString.substring(curTimeToString.length() - 8);
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        //when
        String result = now + curTimeToString;
        //then
        Assertions.assertEquals(result.length(), 14);
    }

}