package com.konnkt.database.service;

import com.konnkt.database.entity.Role;
import com.konnkt.database.dto.RoleDto;
import com.konnkt.database.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements RoleServiceInterface {
    private final RoleRepository roleRepository;

    private RoleDto EntityToDto(Role role) {
        return new RoleDto(role.getRoleId(), role.getRoleName());
    }

    public List<RoleDto> findAll() {
        return roleRepository.findAll()
            .stream()
            .map(this::EntityToDto)
            .toList();
    }

    public RoleDto findById(Long id) {
        return roleRepository.findById(id)
            .map(this::EntityToDto)
            .orElse(null);
    }
}
