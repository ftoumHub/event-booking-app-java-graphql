package com.codewiz.eventbooking.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.dataloader.DataLoader;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.codewiz.eventbooking.dto.EventInput;
import com.codewiz.eventbooking.entity.Artist;
import com.codewiz.eventbooking.entity.Event;
import com.codewiz.eventbooking.entity.EventArtist;
import com.codewiz.eventbooking.entity.Venue;
import com.codewiz.eventbooking.repository.ArtistRepository;
import com.codewiz.eventbooking.repository.EventArtistRepository;
import com.codewiz.eventbooking.repository.EventRepository;
import com.codewiz.eventbooking.repository.VenueRepository;

@Controller
public class EventController {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final ArtistRepository artistRepository;
    private final EventArtistRepository eventArtistRepository;

    public EventController(EventRepository eventRepository, VenueRepository venueRepository,
                         ArtistRepository artistRepository, EventArtistRepository eventArtistRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.artistRepository = artistRepository;
        this.eventArtistRepository = eventArtistRepository;
    }

    @QueryMapping
    public List<Event> events() {
        System.out.println(">>> EVENTS QUERY CALLED");
        return (List<Event>) eventRepository.findAll();
    }

    @QueryMapping
    public Event event(@Argument String id) {
        return eventRepository.findById(Long.valueOf(id)).orElse(null);
    }

    /**@SchemaMapping
    public Venue venue(Event event) {
        return venueRepository.findById(event.venueId()).orElse(null);
    }

    @SchemaMapping
    public List<Artist> artists(Event event) {
        return artistRepository.findByEventId(event.id());
    }*/

    @SchemaMapping
    public CompletableFuture<Venue> venue(Event event, DataLoader<Long, Venue> venueLoader) {
        return venueLoader.load(event.venueId());
    }

    @SchemaMapping
    public CompletableFuture<List<Artist>> artists(Event event,
                                                   DataLoader<Long, List<Artist>> artistLoader) {
        return artistLoader.load(event.id());
    }

    @MutationMapping
    public Event createEvent(@Argument EventInput eventInput) {
        Event event = new Event(eventInput.name(), eventInput.description(), LocalDateTime.parse(eventInput.eventDate()), 
                               eventInput.category(), eventInput.imageUrl(), eventInput.venueId().longValue());
        Event savedEvent = eventRepository.save(event);

        // Create event-artist relationships
        for (Integer artistId : eventInput.artistIds()) {
            EventArtist eventArtist = new EventArtist(savedEvent.id(), artistId.longValue());
            eventArtistRepository.save(eventArtist);
        }

        return savedEvent;
    }
}
