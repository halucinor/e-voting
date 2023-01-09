package com.gabia.evoting.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseUserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(nullable = false)
    protected String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected Role role;

    @Builder
    public BaseUserModel(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public BaseUserModel update(String name) {
        this.name = name;
        return this;
    }
}
