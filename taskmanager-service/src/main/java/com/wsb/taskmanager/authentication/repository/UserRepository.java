package com.wsb.taskmanager.authentication.repository;

import com.wsb.taskmanager.authentication.model.UserBE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserBE, Long> {
    Optional<UserBE> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
