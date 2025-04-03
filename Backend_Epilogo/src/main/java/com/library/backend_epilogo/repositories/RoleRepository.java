package com.library.backend_epilogo.repositories;

import com.library.backend_epilogo.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
    Role findByRoleId(Long roleId);
}