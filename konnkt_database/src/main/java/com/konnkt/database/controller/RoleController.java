package com.konnkt.database.controller;

import com.konnkt.database.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {

    @GetMapping("/getRoles")
    public ResponseEntity<List<RoleDto>> getRoles() {
        return null;
    }

    @GetMapping("/getRole/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        return null;
    }

}
