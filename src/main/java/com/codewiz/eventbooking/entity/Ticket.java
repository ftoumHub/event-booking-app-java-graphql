package com.codewiz.eventbooking.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tickets")
public record Ticket(@Id Long id, Integer seatNo, Long bookingId) {
    public Ticket(Integer seatNo, Long bookingId) {
        this(null, seatNo, bookingId);
    }
}
