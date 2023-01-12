package com.gabia.evoting.web.dto;

import com.gabia.evoting.domain.user.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDto {
    private String email;
    private Role role;
}
