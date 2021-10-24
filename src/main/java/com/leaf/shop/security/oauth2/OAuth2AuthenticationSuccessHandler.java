package com.leaf.shop.security.oauth2;

import com.leaf.shop.security.TokenProvider;
import com.leaf.shop.security.UserPrincipal;
import com.leaf.shop.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.leaf.shop.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.*;

@RequiredArgsConstructor
@Service
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final TokenProvider tokenProvider;
    @Value("${app.front-domain}")
    private String frontDomain;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        clearAuthenticationAttributes(request, response);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String accessToken = tokenProvider.generateTokenByUserId(userPrincipal.getUserId());
        CookieUtils.addCookie(response, TokenProvider.ACCESS_TOKEN_COOKIE_NAME
        , accessToken, (int) TokenProvider.ACCESS_TOKEN_EXPIRATION_SECOND);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectPathName = CookieUtils.getCookie(request, REDIRECT_PATH_COOKIE_NAME)
                .map(Cookie::getValue);
        return UriComponentsBuilder.fromUriString(frontDomain).path(redirectPathName.orElse("/"))
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

}
