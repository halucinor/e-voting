package com.gabia.evoting.service;


import com.gabia.evoting.domain.UserModel;
import com.gabia.evoting.domain.user.BaseUserModel;
import com.gabia.evoting.domain.user.Role;
import com.gabia.evoting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//import javax.transaction.Transactional;

import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Spring Security 필수 메소드 구현
     *
     * @param email 이메일
     * @return UserDetails
     * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
     */
    public BaseUserModel loadUserByUsername(String email) throws UsernameNotFoundException {
        // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException((email)));
    }

    @Transactional
    public UserModel createUser(String email){
        UserModel member = new UserModel("user", email, Role.USER);
        userRepository.save(member);
        return member;
    }
}