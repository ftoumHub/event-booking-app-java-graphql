package com.codewiz.eventbooking.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.codewiz.eventbooking.entity.Event;
import com.codewiz.eventbooking.repository.EventRepository;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventController eventController;

    @Test
    void shouldReturnAllEvents() {
        Event event = new Event(1L, "Test Event", "Test Description", LocalDateTime.now(), "Concert", null, 1L);

        when(eventRepository.findAll()).thenReturn(List.of(event));

        List<Event> events = (List<Event>) eventController.events();
        assertEquals(1, events.size());
        assertEquals("Test Event", events.get(0).name());
    }

    @Test
    void shouldReturnEventById() {
        Event event = new Event(1L, "Test Event", "Test Description", LocalDateTime.now(), "Concert", null, 1L);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Event result = eventController.event("1");
        assertNotNull(result);
        assertEquals("Test Event", result.name());
        assertEquals("Test Description", result.description());
    }
}
