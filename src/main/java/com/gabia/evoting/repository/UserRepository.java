package com.gabia.evoting.repository;

import com.gabia.evoting.domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findById(Long id);

    boolean existsByEmail(String email);
}
