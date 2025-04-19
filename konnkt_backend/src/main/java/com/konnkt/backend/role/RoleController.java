package com.konnkt.backend.role;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/roles")
@SecurityRequirement(name="bearerAuth")
@PreAuthorize("hasAuthority('admin')")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @PostMapping("/createRoles")
    public ResponseEntity<?> addRoles(@RequestBody List<String> roleList) {
        logger.debug("Request to add roles {}", roleList);
        List<String> addedRoles = roleService.addRoles(roleList);
        logger.debug("Successfully added roles {}", addedRoles);
        return new ResponseEntity<>(addedRoles, HttpStatus.OK);
    }

    @GetMapping("/getRoles")
    public ResponseEntity<?> getAllRoles() {
        logger.debug("Request to get all roles");
        List<String> roles = roleService.getAllRoles();
        logger.debug("Successfully retrieved all roles");
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PutMapping("/updateRole/{oldRoleName}")
    public ResponseEntity<?> updateRole(@PathVariable String oldRoleName, @RequestBody String newRoleName) {
        logger.debug("Request to update role {} to {}", oldRoleName, newRoleName);
        roleService.updateRole(oldRoleName, newRoleName);
        logger.debug("Successfully updated role {} to {}", oldRoleName, newRoleName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{roleName}")
    public ResponseEntity<?> deleteRole(@PathVariable String roleName) {
        logger.debug("Request to delete role {}", roleName);
        roleService.deleteRole(roleName);
        logger.debug("Successfully deleted role {}", roleName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
