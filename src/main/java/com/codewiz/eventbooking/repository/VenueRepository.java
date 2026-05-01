package com.codewiz.eventbooking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codewiz.eventbooking.entity.Venue;

import java.util.Collection;
import java.util.List;

@Repository
public interface VenueRepository extends CrudRepository<Venue, Long> {

    List<Venue> findByIdIn(Collection<Long> ids);
}
