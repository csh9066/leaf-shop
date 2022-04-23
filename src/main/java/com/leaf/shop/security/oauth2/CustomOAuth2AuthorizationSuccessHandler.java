package com.leaf.shop.security.oauth2;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomOAuth2AuthorizationSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${app.front-domain}")
    private String frontDomain;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final OAuth2AuthorizationRequestBasedOnSessionRepository oAuth2AuthorizationRequestBasedOnSessionRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String nextUriPath = oAuth2AuthorizationRequestBasedOnSessionRepository.removeNextUriPath(request);
        String redirectUri = frontDomain;
        if (nextUriPath != null) {
            redirectUri = redirectUri + "/" + nextUriPath;
        }

        redirectStrategy.sendRedirect(request, response, redirectUri);
    }
}
