package com.konnkt.backend.config.security;

import com.konnkt.backend.role.Role;
import com.konnkt.backend.user.User;
import com.konnkt.backend.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginInfo) throws UsernameNotFoundException {
        User user;
        try {
            user = userRepository.findUserByEmail(loginInfo)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found with email: " + loginInfo));
        } catch (UsernameNotFoundException e) {
            user = userRepository.findUserByUsername(loginInfo).orElseThrow(() ->
                    new UsernameNotFoundException("User not found with username: " + loginInfo));
        }

        if (user.getRole() == null) {
            throw new UsernameNotFoundException("User has no role assigned: " + loginInfo);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPasswordHash(), mapRolesToAuthorities(Collections.singletonList(user.getRole())));
    }


    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
        return roles.stream().map(role->new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }

}