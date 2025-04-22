package com.konnkt.auth.service;

import com.konnkt.auth.dto.LoginDto;
import com.konnkt.auth.dto.RegisterDto;
import com.konnkt.auth.security.JwtGenerator;
import com.konnkt.auth.entity.Role;
import com.konnkt.auth.entity.User;
import com.konnkt.auth.exception.BadRequestException;
import com.konnkt.auth.repository.RoleRepository;
import com.konnkt.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtGenerator jwtGenerator;


    public void register(RegisterDto registerDto) {

        if(userRepository.existsUserByEmail(registerDto.getEmail())) {
            throw new BadRequestException("Email is already used");
        }
        if(userRepository.existsUserByUsername(registerDto.getUsername())) {
            throw new BadRequestException("Username is already used");
        }

        Role newUserRole = roleRepository.findRoleByRoleName("user").orElseThrow();

        User newUser = new User();
        newUser.setUsername(registerDto.getUsername());
        newUser.setEmail(registerDto.getEmail());
        newUser.setPasswordHash(passwordEncoder.encode(registerDto.getPassword()));
        newUser.setRole(newUserRole);
        newUser.setCreatedAt(LocalDateTime.now());
        userRepository.save(newUser);
    }

    public String login(LoginDto loginDto) {
        User user = userRepository.findUserByEmail(loginDto.getLoginInfo())
                .orElseGet(() -> userRepository.findUserByUsername(loginDto.getLoginInfo())
                        .orElseThrow(() -> new BadRequestException("Wrong credentials")));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        loginDto.getPassword()));
        if (!authentication.isAuthenticated())
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtGenerator.generateToken(authentication);

    }
}
