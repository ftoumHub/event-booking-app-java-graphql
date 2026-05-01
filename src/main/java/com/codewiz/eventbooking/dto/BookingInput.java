package com.codewiz.eventbooking.dto;

import java.util.List;

public record BookingInput(Integer eventId, List<Integer> seats) {}
