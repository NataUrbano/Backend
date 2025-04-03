package com.library.backend_epilogo.services;

import com.library.backend_epilogo.Models.Role;
import com.library.backend_epilogo.Models.User;
import com.library.backend_epilogo.dto.UserRequestDTO;
import com.library.backend_epilogo.dto.UserResponseDTO;
import com.library.backend_epilogo.repositories.RoleRepository;
import com.library.backend_epilogo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Métodos de conversión entre Entity y DTO
    private UserResponseDTO convertToDto(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getIdUser());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setRegisterDate(user.getRegisterDate());

        // Convertir roles a nombres
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getRol)
                .collect(Collectors.toSet());
        dto.setRoles(roleNames);

        return dto;
    }

    private User convertToEntity(UserRequestDTO dto) {
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Asignar roles si existen
        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(dto.getRoleIds()));
            user.setRoles(roles);
        }

        return user;
    }

    // CREATE
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userDto) {
        // Verificar si el email ya existe
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("El email ya está en uso");
        }

        User user = convertToEntity(userDto);
        user.setRegisterDate(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    // READ (One)
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertToDto(user);
    }

    // READ (All)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar campos básicos
        existingUser.setUserName(userDto.getUserName());

        // Actualizar email si es diferente
        if (!existingUser.getEmail().equals(userDto.getEmail())) {
            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new RuntimeException("El nuevo email ya está en uso");
            }
            existingUser.setEmail(userDto.getEmail());
        }

        // Actualizar contraseña si se proporciona
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        // Actualizar roles si se proporcionan
        if (userDto.getRoleIds() != null) {
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(userDto.getRoleIds()));
            existingUser.setRoles(roles);
        }

        User updatedUser = userRepository.save(existingUser);
        return convertToDto(updatedUser);
    }

    // DELETE
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }
}