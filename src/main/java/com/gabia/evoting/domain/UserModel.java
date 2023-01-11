package com.gabia.evoting.domain;

import com.gabia.evoting.domain.user.BaseUserModel;
import com.gabia.evoting.domain.user.Role;
import com.gabia.evoting.web.dto.UserSignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserModel extends BaseUserModel {

    @Column
    private Long voteCount;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<VoteModel> voteModels;

    public UserModel(UserSignupRequestDto request) {
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.role = request.getRole();
    }
    public void encryptPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }
}