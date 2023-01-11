package com.gabia.evoting.auth.jwt.impl;

import com.gabia.evoting.domain.UserModel;
import com.gabia.evoting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("등록되지 않은 사용자 입니다"));

        return new UserDetailsImpl(user);
    }

}
