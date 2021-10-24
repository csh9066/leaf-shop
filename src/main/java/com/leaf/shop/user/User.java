package com.leaf.shop.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private UserProvider provider;

    public User(String email, String nickname, UserProvider provider) {
        this.email = email;
        this.nickname = nickname;
        this.provider = provider;
    }
}
