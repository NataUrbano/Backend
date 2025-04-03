package com.library.backend_epilogo.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
public class UserRequestDTO {
    private String userName;
    private String email;
    private String password;
    private Set<Long> roleIds;

}
