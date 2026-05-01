package com.codewiz.eventbooking.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.codewiz.eventbooking.dto.VenueInput;
import com.codewiz.eventbooking.dto.Weather;
import com.codewiz.eventbooking.entity.Event;
import com.codewiz.eventbooking.entity.Venue;
import com.codewiz.eventbooking.repository.EventRepository;
import com.codewiz.eventbooking.repository.VenueRepository;
import com.codewiz.eventbooking.service.WeatherService;

@Controller
public class VenueController {

    private final VenueRepository venueRepository;
    private final EventRepository eventRepository;
    private final WeatherService weatherService;

    public VenueController(VenueRepository venueRepository, EventRepository eventRepository, WeatherService weatherService) {
        this.venueRepository = venueRepository;
        this.eventRepository = eventRepository;
        this.weatherService = weatherService;
    }

    @QueryMapping
    public List<Venue> venues() {
        return (List<Venue>) venueRepository.findAll();
    }

    @QueryMapping
    public Venue venue(@Argument String id) {
        return venueRepository.findById(Long.valueOf(id)).orElse(null);
    }

    @SchemaMapping
    public List<Event> events(Venue venue) {
        return eventRepository.findByVenueId(venue.id());
    }

    @SchemaMapping
    public Weather weather(Venue venue) {
        return weatherService.getWeatherForLocation(venue.location());
    }

    @MutationMapping
    public Venue createVenue(@Argument VenueInput venueInput) {
        Venue venue = new Venue(venueInput.name(), venueInput.address(), venueInput.location(), venueInput.capacity().intValue());
        return venueRepository.save(venue);
    }
}
