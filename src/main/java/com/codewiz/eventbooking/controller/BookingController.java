package com.codewiz.eventbooking.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.codewiz.eventbooking.dto.BookingInput;
import com.codewiz.eventbooking.entity.Booking;
import com.codewiz.eventbooking.entity.Ticket;
import com.codewiz.eventbooking.repository.BookingRepository;
import com.codewiz.eventbooking.repository.TicketRepository;

@Controller
public class BookingController {

    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;

    public BookingController(BookingRepository bookingRepository, TicketRepository ticketRepository) {
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
    }

    @QueryMapping
    public List<Booking> bookings(@Argument Double eventId) {
        return bookingRepository.findByEventId(eventId.longValue());
    }

    @SchemaMapping
    public List<Ticket> tickets(Booking booking) {
        return ticketRepository.findByBookingId(booking.id());
    }

    @MutationMapping
    public Booking createBooking(@Argument BookingInput bookingInput) {
        Booking booking = new Booking(LocalDateTime.now(), 1L, bookingInput.eventId().longValue(), 50.0 * bookingInput.seats().size());
        Booking savedBooking = bookingRepository.save(booking);

        // Create tickets
        for (Integer seatNo : bookingInput.seats()) {
            Ticket ticket = new Ticket(seatNo, savedBooking.id());
            ticketRepository.save(ticket);
        }

        return savedBooking;
    }
}
