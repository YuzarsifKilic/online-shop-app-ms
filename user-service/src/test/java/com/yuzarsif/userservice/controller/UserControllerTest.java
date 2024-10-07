package com.yuzarsif.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuzarsif.userservice.dto.*;
import com.yuzarsif.userservice.exception.EmailAlreadyInUseException;
import com.yuzarsif.userservice.exception.EntityNotFoundException;
import com.yuzarsif.userservice.model.Role;
import com.yuzarsif.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testCreateUser_WhenRequestIsValid_ShouldReturnUserDto() throws Exception {

        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(mockUserDto());

        mockMvc.perform(post("/api/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateUserRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("first_name"))
                .andExpect(jsonPath("$.lastName").value("last_name"))
                .andExpect(jsonPath("$.email").value("email@gmail.com"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));
    }

    @Test
    public void testCreateUser_WhenRequestIsInvalid_ShouldReturnBadRequest() throws Exception {

        mockMvc.perform(post("/api/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateUserRequestWithInvalidEmail())))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.description").exists());
    }

    @Test
    public void testCreateUser_WhenEmailAlreadyExist_ShouldThrowEmailAlreadyInUseException() throws Exception {

        when(userService.createUser(any(CreateUserRequest.class))).thenThrow(new EmailAlreadyInUseException("Email already in use"));

        mockMvc.perform(post("/api/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateUserRequest())))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("EMAIL_ALREADY_IN_USE"))
                .andExpect(jsonPath("$.description").value("Email already in use"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void testLogin_WhenInformationIsValid_ShouldReturnAuthResponse() throws Exception {

        when(userService.login(any(LoginRequest.class))).thenReturn(mockAuthResponse());

        mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateUserRequest())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("token"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"))
                .andExpect(jsonPath("$.userId").value("user_id"));
    }

    @Test
    public void testLogin_WhenInformationIsInvalid_ShouldThrowUsernameNotFoundException() throws Exception {

        when(userService.login(any(LoginRequest.class))).thenThrow(new UsernameNotFoundException("Invalid credentials"));

        mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateUserRequest())))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("NOT_FOUND_ERROR"))
                .andExpect(jsonPath("$.description").value("Invalid credentials"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void testFindUserById_WhenUserExists_ShouldReturnUserDto() throws Exception {

        when(userService.findUserById(anyString())).thenReturn(mockUserDto());

        mockMvc.perform(get("/api/v1/users/{userId}", "user_id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("first_name"))
                .andExpect(jsonPath("$.lastName").value("last_name"))
                .andExpect(jsonPath("$.email").value("email@gmail.com"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));
    }

    @Test
    public void testFindUserById_WhenUserDoesNotExist_ShouldThrowEntityNotFoundException() throws Exception {

        when(userService.findUserById(anyString())).thenThrow(new EntityNotFoundException("User not found by id: user_id"));

        mockMvc.perform(get("/api/v1/users/{userId}", "user_id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("NOT_FOUND_ERROR"))
                .andExpect(jsonPath("$.description").value("User not found by id: user_id"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void testCheckUserExists_WhenUserExists_ShouldReturnTrue() throws Exception {

        when(userService.userExists(anyString())).thenReturn(true);

        mockMvc.perform(post("/api/v1/users/user-exists/{userId}", "user_id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void testCheckUserExists_WhenUserExists_ShouldReturnFalse() throws Exception {

        when(userService.userExists(anyString())).thenReturn(false);

        mockMvc.perform(post("/api/v1/users/user-exists/{userId}", "user_id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(false));
    }

    private CreateUserRequest mockCreateUserRequest() {
        return new CreateUserRequest("first_name", "last_name", "email@gmail.com", "password");
    }

    private CreateUserRequest mockCreateUserRequestWithInvalidEmail() {
        return new CreateUserRequest("first_name", "last_name", "email", "password");
    }

    private UserDto mockUserDto() {
        return new UserDto("user_id", "first_name", "last_name", "email@gmail.com", Role.ROLE_USER);
    }

    private AuthResponse mockAuthResponse() {
        return new AuthResponse("token", Role.ROLE_USER, "user_id");
    }

    private EmailRequest mockEmailRequest() {
        return new EmailRequest("email@gmail.com");
    }
}
