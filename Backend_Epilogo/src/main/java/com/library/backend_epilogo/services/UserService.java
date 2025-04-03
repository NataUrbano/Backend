package com.library.backend_epilogo.services;

import com.library.backend_epilogo.dto.UserCreateDto;
import com.library.backend_epilogo.dto.UserDto;
import com.library.backend_epilogo.dto.UserUpdateDto;
import com.library.backend_epilogo.exceptions.ResourceNotFoundException;
import com.library.backend_epilogo.models.Role;
import com.library.backend_epilogo.models.User;
import com.library.backend_epilogo.repositories.RoleRepository;
import com.library.backend_epilogo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // Convertir User a UserDto
    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        // Convertir roles a IDs
        if (user.getRoles() != null) {
            Set<Long> roleIds = user.getRoles().stream()
                    .map(Role::getRoleId)
                    .collect(Collectors.toSet());
            dto.setRoleIds(roleIds);
        }

        return dto;
    }

    // Obtener roles a partir de IDs
    private Set<Role> getRolesFromIds(Set<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return new HashSet<>();
        }

        return roleIds.stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + roleId)))
                .collect(Collectors.toSet());
    }

    // Crear usuario
    @Transactional
    public UserDto createUser(UserCreateDto userCreateDto) {
        // Verificar si ya existe un usuario con ese email o username
        if (userRepository.existsByEmail(userCreateDto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        if (userRepository.existsByUsername(userCreateDto.getUsername())) {
            throw new IllegalArgumentException("Username already in use");
        }

        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setEmail(userCreateDto.getEmail());
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));

        // Asignar roles
        Set<Role> roles = getRolesFromIds(userCreateDto.getRoleIds());
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    // Obtener todos los usuarios
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtener usuario por ID
    @Transactional(readOnly = true)
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return convertToDto(user);
    }

    // Actualizar usuario
    @Transactional
    public UserDto updateUser(Long userId, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Verificar email único si se está actualizando
        if (userUpdateDto.getEmail() != null &&
                !userUpdateDto.getEmail().equals(user.getEmail()) &&
                userRepository.existsByEmail(userUpdateDto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Verificar username único si se está actualizando
        if (userUpdateDto.getUsername() != null &&
                !userUpdateDto.getUsername().equals(user.getUsername()) &&
                userRepository.existsByUsername(userUpdateDto.getUsername())) {
            throw new IllegalArgumentException("Username already in use");
        }

        // Actualizar campos si no son nulos
        if (userUpdateDto.getUsername() != null) {
            user.setUsername(userUpdateDto.getUsername());
        }

        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }

        if (userUpdateDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }

        // Actualizar roles si se proporcionan
        if (userUpdateDto.getRoleIds() != null) {
            Set<Role> roles = getRolesFromIds(userUpdateDto.getRoleIds());
            user.setRoles(roles);
        }

        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }

    // Eliminar usuario
    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }
}
