package com.codewiz.eventbooking.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record User(@Id Long id, String name, String email, String password, String role) {
    public User(String name, String email, String password, String role) {
        this(null, name, email, password, role);
    }
}
