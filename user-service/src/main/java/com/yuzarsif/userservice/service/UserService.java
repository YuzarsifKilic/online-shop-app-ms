package com.yuzarsif.userservice.service;

import com.yuzarsif.userservice.dto.*;
import com.yuzarsif.userservice.exception.EmailAlreadyInUseException;
import com.yuzarsif.userservice.exception.EntityNotFoundException;
import com.yuzarsif.userservice.model.Role;
import com.yuzarsif.userservice.model.User;
import com.yuzarsif.userservice.repository.UserRepository;
import com.yuzarsif.userservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public UserDto createUser(CreateUserRequest request) {

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailAlreadyInUseException("Email already in use: " + request.email());
        }

        User user = User
                .builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();

        return UserDto.convert(userRepository.save(user));
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        if (authentication.isAuthenticated()) {
            User user = userRepository.findByEmail(request.email()).get();
            String token = jwtService.generateToken(user);
            return new AuthResponse(
                    token,
                    user.getRole(),
                    user.getId());
        }
        throw new UsernameNotFoundException("Invalid credentials");
    }

    public Boolean checkIfEmailExists(EmailRequest email) {
        return userRepository.findByEmail(email.email()).isPresent();
    }

    public UserDto findUserById(String userId) {
        return UserDto.convert(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId)));
    }

    public Boolean userExists(String userId) {
        return userRepository.findById(userId).isPresent();
    }


    public UserDto getByEmail(EmailRequest email) {
        return UserDto.convert(userRepository.findByEmail(email.email())
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email.email())));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
