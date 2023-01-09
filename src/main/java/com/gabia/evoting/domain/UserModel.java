package com.gabia.evoting.domain;

import com.gabia.evoting.domain.user.BaseUserModel;
import com.gabia.evoting.domain.user.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "H3_USER")
public class UserModel extends BaseUserModel {
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "userId")
//    private List<UserCharacterModel> userCharacters;

    // device info

    // login count

    // current Character

    // character history

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "userId")
//    private List<UserSettingModel> userSettings;

    public UserModel(String name, String email, String picture, Role role) {
        this.name = name;
        this.role = role;
    }

    public UserModel update(String name, String picture) {
        this.name = name;
        return this;
    }
}