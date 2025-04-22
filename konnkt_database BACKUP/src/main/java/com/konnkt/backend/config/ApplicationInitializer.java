package com.konnkt.backend.config;

import com.konnkt.backend.forum.Forum;
import com.konnkt.backend.forum.ForumRepository;
import com.konnkt.backend.role.Role;
import com.konnkt.backend.user.User;
import com.konnkt.backend.role.RoleRepository;
import com.konnkt.backend.user.UserRepository;
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
    private final ForumRepository forumRepository;

    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${admin.email}")
    private String adminEmail;

    public ApplicationInitializer(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, ForumRepository forumRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.forumRepository = forumRepository;
    }

    @Override
    public void run(String... args) throws Exception {
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

        if (forumRepository.findByName("General").isEmpty()) {
            Forum forum = new Forum();
            forum.setName("General");
            forum.setDescription("General forum for all topics and all posts");
            forum.setCreatedAt(LocalDateTime.now());
            forumRepository.save(forum);
        }
        


    }
}
