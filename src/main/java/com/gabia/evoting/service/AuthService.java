package com.gabia.evoting.service;

import com.gabia.evoting.domain.UserModel;
import com.gabia.evoting.domain.user.BaseUserModel;
import com.gabia.evoting.repository.UserRepository;
import com.gabia.evoting.web.dto.JwtRequestDto;
import com.gabia.evoting.web.dto.SignupResponseDto;
import com.gabia.evoting.web.dto.UserSignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public BaseUserModel authenticateByEmailAndPassword(String email, String password){
        UserModel user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException((email)));

        if(!passwordEncoder.matches(password,user.getPassword()))
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");

        return user;
    }

    @Transactional
    public boolean signup(UserSignupRequestDto requestDto){
        boolean existMember = userRepository.existsByEmail(requestDto.getEmail());

        if(existMember)
            return false;

        UserModel member = new UserModel(requestDto);
        member.encryptPassword(passwordEncoder);

        userRepository.save(member);
        return true;
    }

    public String login(JwtRequestDto request) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        BaseUserModel principal = (BaseUserModel) authentication.getPrincipal();
        return principal.getEmail();
    }
}
