package com.leaf.shop.security.oauth2;

import com.leaf.shop.module.cart.CartService;
import com.leaf.shop.security.UserPrincipal;
import com.leaf.shop.module.user.User;
import com.leaf.shop.module.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final CartService cartService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        return processOauth2User(registrationId, oAuth2User);

    }

    @Transactional
    public OAuth2User processOauth2User(String registrationId, OAuth2User oAuth2User) {
        Oauth2UserAttributes oauth2UserAttributes = Oauth2UserAttributes.of(registrationId, oAuth2User.getAttributes());
        Optional<User> findUser = userRepository.findByEmail(oauth2UserAttributes.getEmail());
        User user;
        if (findUser.isPresent()) {
            user = findUser.get();
            if (user.getProvider() != oauth2UserAttributes.getProvider()) {
                throw new OAuth2AuthenticationProcessingException(user.getEmail()+"은 이미 "+user.getProvider() +" 로 회원가입된 이메일 입니다");
            }

        } else {
            user = new User(oauth2UserAttributes.getEmail(), oauth2UserAttributes.getNickname(), oauth2UserAttributes.getProvider());
            userRepository.save(user);
            cartService.createCart(user.getId());
        }
        return UserPrincipal.of(user, oauth2UserAttributes.getAttributes());
    }

}
