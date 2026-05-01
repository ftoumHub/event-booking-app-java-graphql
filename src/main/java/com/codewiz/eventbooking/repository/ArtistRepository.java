package com.codewiz.eventbooking.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codewiz.eventbooking.entity.Artist;

@Repository
public interface ArtistRepository extends CrudRepository<Artist, Long> {
    
    @Query("SELECT a.* FROM artists a " +
           "INNER JOIN event_artists ea ON a.id = ea.artist_id " +
           "WHERE ea.event_id = :eventId")
    List<Artist> findByEventId(@Param("eventId") Long eventId);

    List<Artist> findByIdIn(Collection<Long> ids);
}
