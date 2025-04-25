package com.konnkt.database.service;

import com.konnkt.database.dto.RoleDto;

import java.util.List;

public interface RoleServiceInterface {
    List<RoleDto> findAll();
    RoleDto findById(Long id);
}
