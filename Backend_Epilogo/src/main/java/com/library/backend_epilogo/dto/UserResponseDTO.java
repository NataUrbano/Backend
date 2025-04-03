package com.library.backend_epilogo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String userName;
    private String email;
    private LocalDateTime registerDate;
    private Set<String> roles; // Nombres de los roles

}
