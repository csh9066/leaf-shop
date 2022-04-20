package com.leaf.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserInfoDto {
    private long id;
    private String nickName;
    private String email;
}
