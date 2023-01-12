package com.gabia.evoting.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity(name = "H3_USER")
public class BaseUserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected Role role;

    @Column(nullable = false, length=200)
    protected String password;
    @Column
    private String authToken;

    public BaseUserModel(String email, Role role) {
        this.email = email;
        this.role = role;
    }
}
