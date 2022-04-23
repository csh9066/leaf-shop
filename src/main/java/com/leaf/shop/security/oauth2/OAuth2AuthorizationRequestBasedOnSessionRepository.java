package com.leaf.shop.security.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static java.util.Objects.nonNull;

@Component
public class OAuth2AuthorizationRequestBasedOnSessionRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private static String NEXT_URI_PATH_REQUEST_PARAM = "next_uri";

    private static String NEXT_URI_PATH_SESSION_ATTR_NAME = "NEXT_URI_PATH_SESSION_ATTR_NAME";

    private static String OAUTH2_AUTHORIZATION_REQUEST_SESSION_ATTR_NAME = " OAUTH2_AUTHORIZATION_REQUEST_SESSION_ATTR_NAME";

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.removeAuthorizationRequest(request);
    }

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return getOAuth2AuthorizationRequest(request);
    }

    /*
    *  OAuth2AuthorizationRequest 와
    *  인증 후 redirect 할 URI 를 세션에 저장 한다.
    * */
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute(OAUTH2_AUTHORIZATION_REQUEST_SESSION_ATTR_NAME, authorizationRequest);

        String nextUriPath = request.getParameter(NEXT_URI_PATH_REQUEST_PARAM);
        if (nonNull(nextUriPath)) {
            session.setAttribute(NEXT_URI_PATH_SESSION_ATTR_NAME, nextUriPath);
        }
    }

    public String removeNextUriPath(HttpServletRequest request) {
        String result = (String) request.getSession().getAttribute(NEXT_URI_PATH_SESSION_ATTR_NAME);
        request.getSession().removeAttribute(NEXT_URI_PATH_SESSION_ATTR_NAME);
        return result;
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        OAuth2AuthorizationRequest result = getOAuth2AuthorizationRequest(request);
        request.getSession().removeAttribute(OAUTH2_AUTHORIZATION_REQUEST_SESSION_ATTR_NAME);
        return result;
    }

    private OAuth2AuthorizationRequest getOAuth2AuthorizationRequest(HttpServletRequest request) {
        return (OAuth2AuthorizationRequest) request.getSession().getAttribute(OAUTH2_AUTHORIZATION_REQUEST_SESSION_ATTR_NAME);
    }
}
