package com.leaf.shop.security.oauth2;

import com.leaf.shop.module.user.UserProvider;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class Oauth2UserAttributes {
    private Map<String, Object> attributes;
    private String email;
    private String nickname;
    private UserProvider provider;

    @Builder
    private Oauth2UserAttributes(Map<String, Object> attributes, String email, String nickname, UserProvider provider) {
        this.attributes = attributes;
        this.email = email;
        this.nickname = nickname;
        this.provider = provider;
    }

    public static Oauth2UserAttributes of(String registrationId,
                                          Map<String, Object> attributes) {
        if (registrationId.equals("kakao")) {
            return ofKaKao(attributes);
        }

        return ofGoogle(attributes);
    }

    private static Oauth2UserAttributes ofKaKao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");
        return Oauth2UserAttributes.builder()
                .email(String.valueOf(kakaoAccount.get("email")))
                .attributes(attributes)
                .nickname(nickname)
                .provider(UserProvider.KAKAO)
                .build();
    }

    private static Oauth2UserAttributes ofGoogle(Map<String, Object> attributes) {
        String nickname = (String) attributes.get("name");
        return Oauth2UserAttributes.builder()
                .email(String.valueOf(attributes.get("email")))
                .attributes(attributes)
                .nickname(nickname)
                .provider(UserProvider.GOOGLE)
                .build();
    }
}
