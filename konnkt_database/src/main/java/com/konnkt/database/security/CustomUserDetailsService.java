package com.konnkt.database.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String loginInfo) throws UsernameNotFoundException {
        return new org.springframework.security.core.userdetails.User("username", "pass", new ArrayList<>());
    }

}