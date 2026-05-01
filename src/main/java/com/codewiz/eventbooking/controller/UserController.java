package com.codewiz.eventbooking.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.codewiz.eventbooking.dto.UserInput;
import com.codewiz.eventbooking.entity.User;
import com.codewiz.eventbooking.repository.UserRepository;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @MutationMapping
    public User createUser(@Argument UserInput userInput) {
        User user = new User(userInput.name(), userInput.email(), userInput.password(), 
                           userInput.role() != null ? userInput.role() : "ROLE_USER");
        
        return userRepository.save(user);
    }
}
