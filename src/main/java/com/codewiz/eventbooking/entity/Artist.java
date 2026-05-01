package com.codewiz.eventbooking.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("artists")
public record Artist(@Id Long id, String name, String bio, String imageUrl) {
    public Artist(String name, String bio, String imageUrl) {
        this(null, name, bio, imageUrl);
    }
}
