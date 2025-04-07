package com.library.backend_epilogo.repositories;

<<<<<<< HEAD
import com.library.backend_epilogo.models.Role;
=======
import com.library.backend_epilogo.Models.Role;
>>>>>>> d68c7ecdc5ca0803e12a59a771a7a0cfba133c28
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
<<<<<<< HEAD
    Role findByRoleName(String roleName);
    Role findByRoleId(Long roleId);
}
=======
}
>>>>>>> d68c7ecdc5ca0803e12a59a771a7a0cfba133c28
