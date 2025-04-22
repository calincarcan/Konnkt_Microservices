package com.konnkt.backend.role;

import com.konnkt.backend.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    public void updateRole(String oldName, String newName) {
        if (oldName == null || oldName.isBlank()) {
            logger.info("Old role name shouldn't be empty");
            throw new BadRequestException("Old role name shouldn't be empty");
        }
        if (newName == null || newName.isBlank()) {
            logger.info("New role name shouldn't be empty");
            throw new BadRequestException("New role name shouldn't be empty");
        }
        Role role = roleRepository.findRoleByRoleName(oldName)
                .orElseThrow(() -> {
                    logger.info("Role {} not found", oldName);
                    return new BadRequestException("Role not found");
                });
        role.setRoleName(newName);
        roleRepository.save(role);
    }

    public List<String> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(Role::getRoleName)
                .toList();
    }

    public List<String> addRoles(List<String> roleNameList) {
        if (roleNameList.isEmpty()) {
            logger.info("Role list shouldn't be empty");
            throw new BadRequestException("Role list shouldn't be empty");
        }

        return roleNameList.stream()
                .filter(name -> roleRepository.findRoleByRoleName(name).isEmpty())
                .peek(name -> {
                    Role role = new Role();
                    role.setRoleName(name);
                    roleRepository.save(role);
                })
                .toList();
    }

    public void deleteRole(String roleName) {
        if (roleName.equals("user") || roleName.equals("admin")) {
            logger.info("Role \"{}\" cannot be deleted", roleName);
            throw new BadRequestException("Role cannot be deleted");
        }
        Role role = roleRepository.findRoleByRoleName(roleName)
                .orElseThrow(() -> {
                    logger.info("Role {} not found", roleName);
                    return new BadRequestException("Role not found");
                });

        roleRepository.delete(role);
    }
}
