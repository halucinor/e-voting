package com.gabia.evoting.domain;

import com.gabia.evoting.domain.user.BaseUserModel;
import com.gabia.evoting.domain.user.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "USER")
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
}