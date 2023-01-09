package com.gabia.evoting.auth.jwt;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationTokenProvider {

    /*
    * Http 요청으로부터 token 획득
    * @param Http request
    * @return token
    */
    String parseTokenString(HttpServletRequest request);

    /*
    * 토큰 발급
    * @param userEmail
    * @return token
    */
    AuthenticationToken issue(String userEmail);

    /*
    * 토큰에서 UserEmail 획득
    * @param token
    * @return UserEmail in String
    */
    String getTokenSubject(String token);

    /*
    * 토큰 유효성 검증
    * @param token
    * @return True/False
    */
    boolean validateToken(String token);

    Authentication getAuthentication(String token);
}
