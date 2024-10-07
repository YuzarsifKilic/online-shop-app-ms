package com.yuzarsif.userservice.service;

import com.yuzarsif.userservice.dto.AuthResponse;
import com.yuzarsif.userservice.dto.CreateUserRequest;
import com.yuzarsif.userservice.dto.LoginRequest;
import com.yuzarsif.userservice.dto.UserDto;
import com.yuzarsif.userservice.exception.EmailAlreadyInUseException;
import com.yuzarsif.userservice.exception.EntityNotFoundException;
import com.yuzarsif.userservice.model.Role;
import com.yuzarsif.userservice.model.User;
import com.yuzarsif.userservice.repository.UserRepository;
import com.yuzarsif.userservice.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private  JwtService jwtService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser_WhenRequestIsValid_ShouldReturnUserDto() {

        when(userRepository.save(any(User.class))).thenReturn(mockUser());

        UserDto response = userService.createUser(mockCreateUserRequest());

        assertNotNull(response);
        assertEquals("first_name", response.firstName());
        assertEquals("last_name", response.lastName());
        assertEquals("email@gmail.com", response.email());
        assertEquals(Role.ROLE_USER, response.role());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateUser_WhenEmailAlreadyInUse_ShouldThrowEmailAlreadyInUseException() {

        when(userRepository.findByEmail("email@gmail.com")).thenReturn(Optional.of(mockUser()));

        assertThrows(EmailAlreadyInUseException.class, () -> {
            userService.createUser(mockCreateUserRequest());
        });

        EmailAlreadyInUseException emailAlreadyInUseException = assertThrows(EmailAlreadyInUseException.class, () -> {
            userService.createUser(mockCreateUserRequest());
        });

        assertEquals("Email already in use: " + mockCreateUserRequest().email(), emailAlreadyInUseException.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testLogin_WhenLoginIsSuccessful_ShouldReturnAuthResponse() {

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("email@gmail.com");
        when(authentication.getCredentials()).thenReturn("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        when(jwtService.generateToken("email@gmail.com")).thenReturn("token");

        when(userRepository.findByEmail("email@gmail.com")).thenReturn(Optional.of(mockUser()));

        AuthResponse response = userService.login(mockLoginRequest());

        assertNotNull(response);
        assertEquals("token", response.token());
        assertEquals(Role.ROLE_USER, response.role());
        assertEquals("user_id", response.userId());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken("email@gmail.com");
        verify(userRepository, times(1)).findByEmail("email@gmail.com");
    }

    @Test
    public void testLogin_WhenLoginIsNotSuccessful_ShouldThrowUsernameNotFoundException() {

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () ->
                userService.login(mockLoginRequest()));

        assertEquals("Invalid credentials", usernameNotFoundException.getMessage());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testFindUserById_WhenUserExists_ShouldReturnUserDto() {

        when(userRepository.findById("user_id")).thenReturn(Optional.of(mockUser()));

        UserDto response = userService.findUserById("user_id");

        assertNotNull(response);
        assertEquals("first_name", response.firstName());
        assertEquals("last_name", response.lastName());
        assertEquals("email@gmail.com", response.email());
        assertEquals(Role.ROLE_USER, response.role());
    }

    @Test
    public void testFindUserById_WhenUserDoesNotExist_ShouldThrowUsernameNotFoundException() {

        when(userRepository.findById("user_id")).thenReturn(Optional.empty());

        EntityNotFoundException usernameNotFoundException = assertThrows(EntityNotFoundException.class, () -> {
            userService.findUserById("user_id");
        });

        assertEquals("User not found with id: user_id", usernameNotFoundException.getMessage());
        verify(userRepository, times(1)).findById("user_id");
    }

    @Test
    public void testUserExists_WhenUserExists_ShouldReturnTrue() {

        when(userRepository.findById("user_id")).thenReturn(Optional.of(mockUser()));

        assertTrue(userService.userExists("user_id"));

        verify(userRepository, times(1)).findById("user_id");
    }

    @Test
    public void testUserExists_WhenUserDoesNotExist_ShouldReturnFalse() {

        when(userRepository.findById("user_id")).thenReturn(Optional.empty());

        assertFalse(userService.userExists("user_id"));

        verify(userRepository, times(1)).findById("user_id");
    }


    private UserDto mockUserDto() {
        return new UserDto("user_id", "first_name", "last_name", "email@gmail.com", Role.ROLE_USER);
    }

    private CreateUserRequest mockCreateUserRequest() {
        return new CreateUserRequest("first_name", "last_name", "email@gmail.com", "password");
    }

    private User mockUser() {
        return User
                .builder()
                .id("user_id")
                .firstName("first_name")
                .lastName("last_name")
                .email("email@gmail.com")
                .password("password")
                .role(Role.ROLE_USER)
                .build();
    }

    private LoginRequest mockLoginRequest() {
        return new LoginRequest("email@gmail.com", "password");
    }

    private AuthResponse mockAuthResponse() {
        return new AuthResponse("token", Role.ROLE_USER, "user_id");
    }
}
