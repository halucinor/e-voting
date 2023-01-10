package com.gabia.evoting.domain;

import com.gabia.evoting.domain.user.BaseUserModel;
import com.gabia.evoting.domain.user.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserModel extends BaseUserModel {

    public UserModel(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public UserModel update(String name, String picture) {
        this.name = name;
        return this;
    }

    @Column
    private Long voteCount;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<VoteModel> voteModels;
}