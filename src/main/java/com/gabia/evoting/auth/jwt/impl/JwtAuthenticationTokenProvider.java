package com.gabia.evoting.auth.jwt.impl;

import com.gabia.evoting.auth.jwt.AuthenticationToken;
import com.gabia.evoting.auth.jwt.AuthenticationTokenProvider;
import com.gabia.evoting.domain.user.BaseUserModel;
import com.gabia.evoting.service.UserService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * This token provider expect JWT token which was issued by Hotsix Server
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationTokenProvider implements AuthenticationTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenProvider.class);

    @Value("${spring.jwt.secretKey}")
    private String SECRET_KEY;

    @Value("${spring.jwt.expirationTimeInMs}")
    private long EXPIRATION_TIME_IN_MS;

    private final UserService userService;

    @Override
    public String parseTokenString(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public AuthenticationToken issue(String userEmail) {
        return JwtAuthenticationToken.builder()
                .token(buildToken(userEmail))
                .build();
    }

    private String buildToken(String userEmail){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plus(EXPIRATION_TIME_IN_MS, ChronoUnit.MILLIS);
        return Jwts.builder()
                .setSubject(String.valueOf(userEmail))
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    @Override
    public Authentication getAuthentication(String token) {
        BaseUserModel user = userService.loadUserByUsername(getTokenSubject(token));
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRole().getKey());
        return new UsernamePasswordAuthenticationToken(user, "", authorities);
    }

    @Override
    public String getTokenSubject(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    @Override
    public boolean validateToken(String token) {
        if(StringUtils.hasText(token)){
           try {
               Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws((token));
               return true;
           }catch (SignatureException e){
               logger.error("Invalid JWT signature",e);
           }catch (MalformedJwtException e){
               logger.error("Invalid JWT token", e);
           }catch (ExpiredJwtException e){
               logger.error("Expired JWT token", e);
           }catch (UnsupportedJwtException e){
               logger.error("Unsupported JWT token", e);
           }catch (IllegalArgumentException e){
               logger.error("JWT claims string is empty.", e);
           }
        }
        return false;
    }
}