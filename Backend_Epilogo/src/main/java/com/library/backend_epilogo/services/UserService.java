package com.library.backend_epilogo.services;

<<<<<<< HEAD
import com.library.backend_epilogo.dto.UserCreateDto;
import com.library.backend_epilogo.dto.UserDto;
import com.library.backend_epilogo.dto.UserUpdateDto;
import com.library.backend_epilogo.exceptions.ResourceNotFoundException;
import com.library.backend_epilogo.models.Role;
import com.library.backend_epilogo.models.User;
=======
import com.library.backend_epilogo.Models.Role;
import com.library.backend_epilogo.Models.User;
import com.library.backend_epilogo.dto.UserRequestDTO;
import com.library.backend_epilogo.dto.UserResponseDTO;
>>>>>>> d68c7ecdc5ca0803e12a59a771a7a0cfba133c28
import com.library.backend_epilogo.repositories.RoleRepository;
import com.library.backend_epilogo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<<<<<<< HEAD
=======
import java.time.LocalDateTime;
>>>>>>> d68c7ecdc5ca0803e12a59a771a7a0cfba133c28
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
<<<<<<< HEAD

=======
>>>>>>> d68c7ecdc5ca0803e12a59a771a7a0cfba133c28
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

<<<<<<< HEAD
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
=======
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
>>>>>>> d68c7ecdc5ca0803e12a59a771a7a0cfba133c28

        return dto;
    }

<<<<<<< HEAD
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

=======
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
>>>>>>> d68c7ecdc5ca0803e12a59a771a7a0cfba133c28
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

<<<<<<< HEAD
    // Obtener todos los usuarios
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
=======
    // READ (One)
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertToDto(user);
    }

    // READ (All)
    public List<UserResponseDTO> getAllUsers() {
>>>>>>> d68c7ecdc5ca0803e12a59a771a7a0cfba133c28
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

<<<<<<< HEAD
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
=======
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
>>>>>>> d68c7ecdc5ca0803e12a59a771a7a0cfba133c28
