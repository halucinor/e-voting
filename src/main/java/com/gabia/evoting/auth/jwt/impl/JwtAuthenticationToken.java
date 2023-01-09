package com.gabia.evoting.auth.jwt.impl;

import com.gabia.evoting.auth.jwt.AuthenticationToken;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtAuthenticationToken implements AuthenticationToken {

    private String token;

}