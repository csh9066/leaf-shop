package com.leaf.shop.domain.user;

import com.leaf.shop.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private UserProvider provider;

    @Builder
    public User(String email, String nickname, UserProvider provider) {
        this.email = email;
        this.nickname = nickname;
        this.provider = provider;
    }
}
