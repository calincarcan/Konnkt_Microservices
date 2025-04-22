package com.konnkt.backend.config;

import com.konnkt.backend.entity.Role;
import com.konnkt.backend.entity.User;
import com.konnkt.backend.repository.RoleRepository;
import com.konnkt.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ApplicationInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${admin.email}")
    private String adminEmail;

    public ApplicationInitializer(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        Optional<User> admin = userRepository.findUserByEmail(adminEmail);
        if (admin.isEmpty()) {
            Optional<Role> adminRole = roleRepository.findRoleByRoleName("admin");
            if (adminRole.isEmpty()) {
                throw new RuntimeException("Admin role not found in the database!");
            }

            User initAdmin = new User();
            initAdmin.setUsername(adminUsername);
            initAdmin.setEmail(adminEmail);
            initAdmin.setPasswordHash(passwordEncoder.encode(adminPassword));
            initAdmin.setRole(roleRepository.findRoleByRoleName("admin").orElseThrow());
            initAdmin.setCreatedAt(LocalDateTime.now());
            userRepository.save(initAdmin);
        }
    }
}
