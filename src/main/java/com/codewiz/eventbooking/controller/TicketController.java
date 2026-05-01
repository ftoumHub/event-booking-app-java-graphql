package com.codewiz.eventbooking.controller;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.codewiz.eventbooking.entity.Booking;
import com.codewiz.eventbooking.entity.Ticket;
import com.codewiz.eventbooking.repository.BookingRepository;

@Controller
public class TicketController {

    private final BookingRepository bookingRepository;

    public TicketController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @SchemaMapping
    public Booking booking(Ticket ticket) {
        return bookingRepository.findById(ticket.bookingId()).orElse(null);
    }
}
