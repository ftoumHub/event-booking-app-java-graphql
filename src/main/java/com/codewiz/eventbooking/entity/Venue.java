package com.codewiz.eventbooking.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("venues")
public record Venue(@Id Long id, String name, String address, String location, Integer capacity) {
    public Venue(String name, String address, String location, Integer capacity) {
        this(null, name, address, location, capacity);
    }
}
