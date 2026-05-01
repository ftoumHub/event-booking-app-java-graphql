package com.codewiz.eventbooking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codewiz.eventbooking.entity.Venue;

@Repository
public interface VenueRepository extends CrudRepository<Venue, Long> {
}
