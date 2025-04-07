package com.library.backend_epilogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackendEpilogoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendEpilogoApplication.class, args);
    }
<<<<<<< HEAD
=======
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
>>>>>>> d68c7ecdc5ca0803e12a59a771a7a0cfba133c28
}
