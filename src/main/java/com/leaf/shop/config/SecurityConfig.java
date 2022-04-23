package com.leaf.shop.config;

import com.leaf.shop.security.RestAuthenticationEntryPoint;
import com.leaf.shop.security.oauth2.CustomOauth2AuthorizationFailureHandler;
import com.leaf.shop.security.oauth2.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final CustomOauth2UserService customOauth2UserService;
    private final AuthorizationRequestRepository oAuth2AuthorizationRequestBasedOnSessionRepository;
    private final AuthenticationSuccessHandler customOAuth2AuthorizationSuccessHandler;
    private final AuthenticationFailureHandler customOauth2AuthorizationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                formLogin().disable()
                .csrf().disable()
                .cors()
                .and()
                .httpBasic().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(restAuthenticationEntryPoint)
                    .and()
                .authorizeRequests()
                    .antMatchers("/oauth2/**").permitAll()
                    .antMatchers("/api/**").permitAll()
                    .anyRequest().authenticated();

        http.oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnSessionRepository)
                .and()
                .userInfoEndpoint()
                .userService(customOauth2UserService)
                .and()
                .successHandler(customOAuth2AuthorizationSuccessHandler)
                .failureHandler(customOauth2AuthorizationFailureHandler);
    }
}
