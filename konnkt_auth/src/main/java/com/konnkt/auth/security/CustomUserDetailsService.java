package com.konnkt.auth.security;

import com.konnkt.auth.entity.Role;
import com.konnkt.auth.entity.User;
import com.konnkt.auth.repository.UserRepository;
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