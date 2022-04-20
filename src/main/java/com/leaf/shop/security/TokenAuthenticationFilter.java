package com.leaf.shop.security;

import com.leaf.shop.domain.user.User;
import com.leaf.shop.repository.UserRepository;
import com.leaf.shop.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        getAccessToken(request).ifPresent(
                (token) -> {
                    try {
                        if (tokenProvider.isValidateToken(token)) {
                            Long userId = tokenProvider.getUserIdFromToken(token);
                            User user = userRepository.findById(userId).
                                    orElseThrow(() -> new RuntimeException("userId : " +userId+"은 존재하지 않습니다."));
                            UserDetails userDetails = UserPrincipal.of(user);
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                     catch (Exception ex) {
                         log.error("시큐리티 컨텍스트를 생성하는데 실패했습니다.",ex);
                     }
                }
        );
        filterChain.doFilter(request,response);
    }

    private Optional<String> getAccessToken(HttpServletRequest request) {
        return CookieUtils.getCookie(request, TokenProvider.ACCESS_TOKEN_COOKIE_NAME)
                .map(Cookie::getValue);
    }
}
