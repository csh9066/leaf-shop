package com.leaf.shop.security.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomOauth2AuthorizationFailureHandler implements AuthenticationFailureHandler {
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Value("${app.front-domain}")
    private String frontDomain;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("OAuth2 인증에 실패 했습니다.", exception);
        UriComponentsBuilder redirectUri = UriComponentsBuilder.fromUriString(frontDomain);
        redirectUri.path("error")
                .queryParam("code", "인증에 실패 하셨습니다");

        redirectStrategy.sendRedirect(request,response, redirectUri.toUriString());
    }
}
