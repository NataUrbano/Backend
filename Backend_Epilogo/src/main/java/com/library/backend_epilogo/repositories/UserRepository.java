package com.library.backend_epilogo.repositories;

<<<<<<< HEAD
import com.library.backend_epilogo.models.User;
=======
import com.library.backend_epilogo.Models.User;
>>>>>>> d68c7ecdc5ca0803e12a59a771a7a0cfba133c28
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
<<<<<<< HEAD
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
=======
>>>>>>> d68c7ecdc5ca0803e12a59a771a7a0cfba133c28
}
