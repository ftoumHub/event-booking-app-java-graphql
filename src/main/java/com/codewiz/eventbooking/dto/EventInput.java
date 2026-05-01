package com.codewiz.eventbooking.dto;

import java.util.List;

public record EventInput(String name, String description, String eventDate, String category, 
                        String imageUrl, Integer venueId, List<Integer> artistIds) {}
