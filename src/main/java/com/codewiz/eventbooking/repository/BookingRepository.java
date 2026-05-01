package com.codewiz.eventbooking.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codewiz.eventbooking.entity.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
    
    @Query("SELECT * FROM bookings WHERE event_id = :eventId")
    List<Booking> findByEventId(@Param("eventId") Long eventId);
    
    @Query("SELECT * FROM bookings WHERE user_id = :userId")
    List<Booking> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT * FROM bookings WHERE event_id = :eventId AND user_id = :userId")
    List<Booking> findByEventIdAndUserId(@Param("eventId") Long eventId, @Param("userId") Long userId);
}
