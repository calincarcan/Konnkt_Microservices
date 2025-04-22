package com.konnkt.backend.user;

import com.konnkt.backend.exception.BadRequestException;
import com.konnkt.backend.role.Role;
import com.konnkt.backend.role.RoleRepository;
import com.konnkt.backend.user.dto.PublicUserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new BadRequestException("User not found");
        userRepository.delete(user.get());
    }
    public void updateUserName(Long id, String username) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new BadRequestException("User not found");
        if (userRepository.existsUserByUsername(username))
            throw new BadRequestException("Username already exists");
        user.get().setUsername(username);
        userRepository.save(user.get());
    }

    public void updateUserEmail(Long id, String email) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new BadRequestException("User not found");
        if (userRepository.existsUserByEmail(email))
            throw new BadRequestException("Email already exists");
        user.get().setEmail(email);
        userRepository.save(user.get());
    }

    public void updateUserRole(Long id, String roleName) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new BadRequestException("User not found");
        Optional<Role> role = roleRepository.findRoleByRoleName(roleName);
        if (role.isEmpty())
            throw new BadRequestException("Role not found");
        user.get().setRole(role.get());
        userRepository.save(user.get());
    }

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<PublicUserDto> getAllUsers() {
        ArrayList<PublicUserDto> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            PublicUserDto publicUserDto = new PublicUserDto(
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole().getRoleName()
            );
            users.add(publicUserDto);
        });
        return users;
    }

    public PublicUserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            return null;
        return new PublicUserDto(
                user.get().getUsername(),
                user.get().getEmail(),
                user.get().getRole().getRoleName()
        );
    }
}
