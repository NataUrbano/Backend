package com.library.backend_epilogo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
<<<<<<< HEAD
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
=======
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
>>>>>>> d68c7ecdc5ca0803e12a59a771a7a0cfba133c28
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
<<<<<<< HEAD
                .csrf().disable() // Desactiva CSRF para simplicidad en desarrollo
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login").permitAll() // Habilita acceso público a estas rutas
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Solo ADMIN puede acceder a estas rutas
                        .requestMatchers("/api/users/**").authenticated() // Necesita estar autenticado
                        .anyRequest().authenticated() // Otras solicitudes requieren autenticación
                )
                .httpBasic(); // Utiliza autenticación básica para proteger los endpoints

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Define usuarios en memoria
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123")) // Codifica la contraseña
                .roles("ADMIN") // Rol "ADMIN"
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password123"))
                .roles("USER") // Rol "USER"
                .build();

        return new InMemoryUserDetailsManager(admin, user); // Usa usuarios in-memory
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Codificador para contraseñas
    }
=======
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para APIs REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/**").permitAll() // Permitir acceso sin autenticación
                        .anyRequest().authenticated()
                );

        return http.build();
    }
>>>>>>> d68c7ecdc5ca0803e12a59a771a7a0cfba133c28
}