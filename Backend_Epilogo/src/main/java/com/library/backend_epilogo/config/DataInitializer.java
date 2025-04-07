package com.library.backend_epilogo.config;

import com.library.backend_epilogo.models.Role;
import com.library.backend_epilogo.models.User;
import com.library.backend_epilogo.repositories.RoleRepository;
import com.library.backend_epilogo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        try{
            // Inicializar solo si no existen roles
            if (roleRepository.count() == 0 && userRepository.count() == 0) {
                System.out.println("Inicializando roles en la base de datos...");

                // Crear roles
                Role userRole = new Role();
                userRole.setRoleName("USUARIO");
                roleRepository.save(userRole);

                Role adminRole = new Role();
                adminRole.setRoleName("ADMINISTRADOR");
                roleRepository.save(adminRole);

                System.out.println("Roles creados exitosamente!");

                // Opcionalmente crear un usuario administrador por defecto
                if (userRepository.count() == 0) {
                    System.out.println("Creando usuario administrador por defecto...");

                    User adminUser = new User();
                    adminUser.setUsername("admin");
                    adminUser.setEmail("admin@example.com");
                    adminUser.setPassword(passwordEncoder.encode("admin123"));

                    Set<Role> roles = new HashSet<>();
                    roles.add(adminRole);
                    adminUser.setRoles(roles);

                    userRepository.save(adminUser);

                    System.out.println("Usuario administrador creado exitosamente!");
                }

            }
        } catch (Exception e) {
            System.err.println("Error en DataInitializer: " + e.getMessage());
            e.printStackTrace();
        }

    }
}