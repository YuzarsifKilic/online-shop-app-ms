package com.yuzarsif.userservice.controller;

import com.yuzarsif.userservice.dto.AuthResponse;
import com.yuzarsif.userservice.dto.CreateUserRequest;
import com.yuzarsif.userservice.dto.LoginRequest;
import com.yuzarsif.userservice.dto.UserDto;
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
    public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserRequest request) {
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable String userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @PostMapping("/user-exists")
    public ResponseEntity<Boolean> checkIfEmailExists(@RequestBody String email) {
        return ResponseEntity.ok(userService.checkIfEmailExists(email));
    }
}
