package com.codewiz.eventbooking.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codewiz.eventbooking.entity.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    
    @Query("SELECT * FROM events ORDER BY event_date ASC")
    List<Event> findAllOrderByEventDate();
    
    @Query("SELECT * FROM events WHERE venue_id = :venueId")
    List<Event> findByVenueId(@Param("venueId") Long venueId);
}
