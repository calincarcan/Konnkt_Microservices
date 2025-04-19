package com.konnkt.backend.user;

import com.konnkt.backend.user.dto.PublicUserDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUsers")
    public ResponseEntity<?> getAllUsers() {
        List<PublicUserDto> users = userService.getAllUsers();
        logger.debug("Successfully retrieved all users");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        PublicUserDto user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        logger.debug("Successfully retrieved user with id " + id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/updateUserName/{id}")
    @PreAuthorize("hasAuthority('admin')")
    @SecurityRequirement(name="bearerAuth")
    public ResponseEntity<?> updateUserName(@PathVariable Long id, @RequestBody String username) {
        userService.updateUserName(id, username);
        return new ResponseEntity<>("Username updated successfully", HttpStatus.OK);
    }

    @PutMapping("/updateUserEmail/{id}")
    @PreAuthorize("hasAuthority('admin')")
    @SecurityRequirement(name="bearerAuth")
    public ResponseEntity<?> updateUserEmail(@PathVariable Long id, @RequestBody String email) {
        userService.updateUserEmail(id, email);
        return new ResponseEntity<>("Email updated successfully", HttpStatus.OK);
    }

    @PutMapping("/updateUserRole/{id}")
    @PreAuthorize("hasAuthority('admin')")
    @SecurityRequirement(name="bearerAuth")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestBody String roleId) {
        userService.updateUserRole(id, roleId);
        return new ResponseEntity<>("Role updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasAuthority('admin')")
    @SecurityRequirement(name="bearerAuth")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        logger.debug("Successfully deleted user with id " + id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}
