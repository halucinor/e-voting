package com.gabia.evoting.service;

import com.gabia.evoting.auth.jwt.AuthenticationToken;
import com.gabia.evoting.auth.jwt.AuthenticationTokenProvider;
import com.gabia.evoting.auth.jwt.impl.JwtAuthenticationTokenProvider;
import com.gabia.evoting.auth.jwt.impl.UserDetailsImpl;
import com.gabia.evoting.domain.UserModel;
import com.gabia.evoting.domain.user.BaseUserModel;
import com.gabia.evoting.repository.UserRepository;
import com.gabia.evoting.web.dto.JwtRequestDto;
import com.gabia.evoting.web.dto.JwtResponseDto;
import com.gabia.evoting.web.dto.SignupResponseDto;
import com.gabia.evoting.web.dto.UserSignupRequestDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final AuthenticationTokenProvider jwtAuthenticationTokenProvider;

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

    public JwtResponseDto login(JwtRequestDto request) throws Exception {
//        UsernamePasswordAuthenticationToken test = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

//        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl principal = (UserDetailsImpl)  authentication.getPrincipal();
        AuthenticationToken token = jwtAuthenticationTokenProvider.issue(principal.getUsername());

        return new JwtResponseDto(token.getToken());
    }
}
