package com.codewiz.eventbooking.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.codewiz.eventbooking.dto.UserInput;
import com.codewiz.eventbooking.entity.User;
import com.codewiz.eventbooking.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Test
    void shouldCreateUser() {
        User user = new User(1L, "Test User", "test@example.com", "password", "ROLE_USER");

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserInput userInput = new UserInput("Test User", "test@example.com", "password", "ROLE_USER");

        User result = userController.createUser(userInput);
        assertNotNull(result);
        assertEquals("Test User", result.name());
        assertEquals("test@example.com", result.email());
    }
}
