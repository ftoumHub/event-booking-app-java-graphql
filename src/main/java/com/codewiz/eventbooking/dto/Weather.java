package com.codewiz.eventbooking.dto;

public record Weather(Double temp, Double feels_like, Double temp_min, Double temp_max,
                      Integer humidity, String description) {}
