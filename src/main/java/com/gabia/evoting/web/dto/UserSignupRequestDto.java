package com.gabia.evoting.web.dto;


import com.gabia.evoting.domain.user.BaseUserModel;
import com.gabia.evoting.domain.user.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequestDto {

    private String email;
    private String password;
    private Role role;
}
