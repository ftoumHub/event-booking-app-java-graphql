package com.codewiz.eventbooking.integration;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.codewiz.eventbooking.entity.Event;
import com.codewiz.eventbooking.entity.Venue;
import com.codewiz.eventbooking.repository.EventRepository;
import com.codewiz.eventbooking.repository.VenueRepository;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EventBookingIntegrationTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Test
    void shouldCreateAndRetrieveVenue() {
        // Create a venue
        Venue venue = new Venue("Test Venue", "123 Test St", "Test City", 1000);
        Venue savedVenue = venueRepository.save(venue);

        // Verify the venue was saved
        assertNotNull(savedVenue.id());
        assertEquals("Test Venue", savedVenue.name());
        assertEquals("123 Test St", savedVenue.address());
    }

    @Test
    void shouldCreateAndRetrieveEvent() {
        // Create a venue first
        Venue venue = new Venue("Test Venue", "123 Test St", "Test City", 1000);
        Venue savedVenue = venueRepository.save(venue);

        // Create an event
        Event event = new Event("Test Event", "Test Description", LocalDateTime.now().plusDays(30), "Concert", null, savedVenue.id());
        Event savedEvent = eventRepository.save(event);

        // Verify the event was saved
        assertNotNull(savedEvent.id());
        assertEquals("Test Event", savedEvent.name());
        assertEquals("Test Description", savedEvent.description());
    }
}
