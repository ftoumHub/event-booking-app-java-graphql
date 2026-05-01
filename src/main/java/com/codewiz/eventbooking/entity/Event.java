package com.codewiz.eventbooking.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("events")
public record Event(@Id Long id, String name, String description, LocalDateTime eventDate, 
                   String category, String imageUrl, Long venueId) {
    public Event(String name, String description, LocalDateTime eventDate, String category, String imageUrl, Long venueId) {
        this(null, name, description, eventDate, category, imageUrl, venueId);
    }
}
