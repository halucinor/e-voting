package com.gabia.evoting.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Cannot define as a bean because Spring Security the filter this twice
// https://stackoverflow.com/questions/39314176/filter-invoke-twice-when-register-as-spring-bean
//

@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends GenericFilterBean  {
    private final AuthenticationTokenProvider authenticationTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        // 헤더에서 JWT 받아옴
        String token = authenticationTokenProvider.parseTokenString((HttpServletRequest) request);

        if (authenticationTokenProvider.validateToken(token)) {
            Authentication authentication = authenticationTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}