package com.gabia.evoting.web;

import com.gabia.evoting.auth.jwt.impl.JwtAuthenticationToken;
import com.gabia.evoting.repository.UserRepository;
import com.gabia.evoting.service.AuthService;
import com.gabia.evoting.service.UserService;
import com.gabia.evoting.web.dto.JwtRequestDto;
import com.gabia.evoting.web.dto.JwtResponseDto;
import com.gabia.evoting.web.dto.ResponseMessageDto;
import com.gabia.evoting.web.dto.UserSignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController extends AbstractController{

    private final AuthService authService;

    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessageDto<JwtResponseDto> login(@RequestBody JwtRequestDto request) {
        try {
            return successMessage(authService.login(request));
        }catch (Exception e){
            return  failureMessage(new JwtResponseDto(e.getMessage()));
        }
    }

    @PostMapping(value = "signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessageDto signup(@RequestBody UserSignupRequestDto request) {
        if(authService.signup(request)){
            return successMessage(request.getEmail());
        }else{
            return failureMessage(request.getEmail());
        }
    }
}
