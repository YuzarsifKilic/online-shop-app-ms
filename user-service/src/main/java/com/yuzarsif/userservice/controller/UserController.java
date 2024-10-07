package com.yuzarsif.userservice.controller;

import com.yuzarsif.userservice.dto.*;
import com.yuzarsif.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable String userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @PostMapping("/user-exists/{userId}")
    public ResponseEntity<Boolean> checkUserExists(@PathVariable String userId) {
        return ResponseEntity.ok(userService.userExists(userId));
    }
}
