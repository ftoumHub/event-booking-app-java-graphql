package com.codewiz.eventbooking.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("event_artists")
public record EventArtist(@Id Long id, Long eventId, Long artistId) {
    public EventArtist(Long eventId, Long artistId) {
        this(null, eventId, artistId);
    }
}
