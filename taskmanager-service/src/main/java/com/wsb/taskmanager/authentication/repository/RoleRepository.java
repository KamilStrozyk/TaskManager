package com.wsb.taskmanager.authentication.repository;

import com.wsb.taskmanager.authentication.model.Role;
import com.wsb.taskmanager.authentication.model.RoleBE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleBE, Long> {
    Optional<RoleBE> findByRole(Role role);
}
