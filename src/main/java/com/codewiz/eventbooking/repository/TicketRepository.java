package com.codewiz.eventbooking.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codewiz.eventbooking.entity.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    
    @Query("SELECT * FROM tickets WHERE booking_id = :bookingId")
    List<Ticket> findByBookingId(@Param("bookingId") Long bookingId);
    
    @Query("SELECT t.* FROM tickets t " +
           "INNER JOIN bookings b ON t.booking_id = b.id " +
           "WHERE b.event_id = :eventId")
    List<Ticket> findByEventId(@Param("eventId") Long eventId);
}
