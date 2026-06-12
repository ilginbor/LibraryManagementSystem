package com.ilginbor.LibraryManagementSystem.role.repository;

import com.ilginbor.LibraryManagementSystem.user.entity.Role;
import com.ilginbor.LibraryManagementSystem.user.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
