package com.codewiz.eventbooking.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("bookings")
public record Booking(@Id Long id, LocalDateTime bookingDate, Long userId, Long eventId, Double price) {
    public Booking(LocalDateTime bookingDate, Long userId, Long eventId, Double price) {
        this(null, bookingDate, userId, eventId, price);
    }
}
