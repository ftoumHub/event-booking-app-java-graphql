package com.codewiz.eventbooking.integration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.codewiz.eventbooking.entity.Booking;
import com.codewiz.eventbooking.entity.Event;
import com.codewiz.eventbooking.entity.User;
import com.codewiz.eventbooking.entity.Venue;
import com.codewiz.eventbooking.repository.BookingRepository;
import com.codewiz.eventbooking.repository.EventRepository;
import com.codewiz.eventbooking.repository.UserRepository;
import com.codewiz.eventbooking.repository.VenueRepository;

@Testcontainers
@SpringBootTest
@ActiveProfiles("testcontainers")
@Transactional
class EventBookingTestcontainersTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("event_booking_test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() {
        // Clean up data before each test
        bookingRepository.deleteAll();
        eventRepository.deleteAll();
        venueRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateAndRetrieveVenue() {
        // Create a venue
        Venue venue = new Venue("Test Venue", "123 Test St", "Test City", 1000);
        Venue savedVenue = venueRepository.save(venue);

        // Verify the venue was saved
        assertNotNull(savedVenue.id());
        assertEquals("Test Venue", savedVenue.name());
        assertEquals("123 Test St", savedVenue.address());
        assertEquals("Test City", savedVenue.location());
        assertEquals(1000, savedVenue.capacity());
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
        assertEquals("Concert", savedEvent.category());
        assertEquals(savedVenue.id(), savedEvent.venueId());
    }

    @Test
    void shouldCreateAndRetrieveUser() {
        // Create a user
        User user = new User("Test User", "test@example.com", "password123", "ROLE_USER");
        User savedUser = userRepository.save(user);

        // Verify the user was saved
        assertNotNull(savedUser.id());
        assertEquals("Test User", savedUser.name());
        assertEquals("test@example.com", savedUser.email());
        assertEquals("ROLE_USER", savedUser.role());
    }

    @Test
    void shouldCreateAndRetrieveBooking() {
        // Create a user
        User user = new User("Test User", "test@example.com", "password123", "ROLE_USER");
        User savedUser = userRepository.save(user);

        // Create a venue
        Venue venue = new Venue("Test Venue", "123 Test St", "Test City", 1000);
        Venue savedVenue = venueRepository.save(venue);

        // Create an event
        Event event = new Event("Test Event", "Test Description", LocalDateTime.now().plusDays(30), "Concert", null, savedVenue.id());
        Event savedEvent = eventRepository.save(event);

        // Create a booking
        Booking booking = new Booking(LocalDateTime.now(), savedUser.id(), savedEvent.id(), 150.00);
        Booking savedBooking = bookingRepository.save(booking);

        // Verify the booking was saved
        assertNotNull(savedBooking.id());
        assertEquals(savedUser.id(), savedBooking.userId());
        assertEquals(savedEvent.id(), savedBooking.eventId());
        assertEquals(150.00, savedBooking.price());
    }

    @Test
    void shouldFindEventsByVenue() {
        // Create a venue
        Venue venue = new Venue("Test Venue", "123 Test St", "Test City", 1000);
        Venue savedVenue = venueRepository.save(venue);

        // Create events for this venue
        Event event1 = new Event("Event 1", "Description 1", LocalDateTime.now().plusDays(30), "Concert", null, savedVenue.id());
        eventRepository.save(event1);

        Event event2 = new Event("Event 2", "Description 2", LocalDateTime.now().plusDays(45), "Theater", null, savedVenue.id());
        eventRepository.save(event2);

        // Find events by venue
        List<Event> events = eventRepository.findByVenueId(savedVenue.id());
        assertEquals(2, events.size());
        assertTrue(events.stream().anyMatch(e -> e.name().equals("Event 1")));
        assertTrue(events.stream().anyMatch(e -> e.name().equals("Event 2")));
    }

    @Test
    void shouldFindUserByEmail() {
        // Create a user
        User user = new User("Test User", "test@example.com", "password123", "ROLE_USER");
        userRepository.save(user);

        // Find user by email
        Optional<User> foundUser = userRepository.findByEmail("test@example.com");
        assertTrue(foundUser.isPresent());
        assertEquals("Test User", foundUser.get().name());
        assertEquals("test@example.com", foundUser.get().email());
    }

    @Test
    void shouldFindBookingsByEvent() {
        // Create a user
        User user = new User("Test User", "test@example.com", "password123", "ROLE_USER");
        User savedUser = userRepository.save(user);

        // Create a venue
        Venue venue = new Venue("Test Venue", "123 Test St", "Test City", 1000);
        Venue savedVenue = venueRepository.save(venue);

        // Create an event
        Event event = new Event("Test Event", "Test Description", LocalDateTime.now().plusDays(30), "Concert", null, savedVenue.id());
        Event savedEvent = eventRepository.save(event);

        // Create bookings for this event
        Booking booking1 = new Booking(LocalDateTime.now(), savedUser.id(), savedEvent.id(), 150.00);
        bookingRepository.save(booking1);

        Booking booking2 = new Booking(LocalDateTime.now().plusHours(1), savedUser.id(), savedEvent.id(), 200.00);
        bookingRepository.save(booking2);

        // Find bookings by event
        List<Booking> bookings = bookingRepository.findByEventId(savedEvent.id());
        assertEquals(2, bookings.size());
        assertTrue(bookings.stream().anyMatch(b -> b.price() == 150.00));
        assertTrue(bookings.stream().anyMatch(b -> b.price() == 200.00));
    }
}
