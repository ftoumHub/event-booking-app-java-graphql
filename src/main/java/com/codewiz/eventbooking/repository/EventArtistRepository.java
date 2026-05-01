package com.codewiz.eventbooking.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codewiz.eventbooking.entity.EventArtist;

@Repository
public interface EventArtistRepository extends CrudRepository<EventArtist, Long> {
    
    @Query("SELECT * FROM event_artists WHERE event_id = :eventId")
    List<EventArtist> findByEventId(@Param("eventId") Long eventId);
    
    @Query("SELECT * FROM event_artists WHERE artist_id = :artistId")
    List<EventArtist> findByArtistId(@Param("artistId") Long artistId);
    
    @Modifying
    @Query("DELETE FROM event_artists WHERE event_id = :eventId")
    void deleteByEventId(@Param("eventId") Long eventId);

    List<EventArtist> findByEventIdIn(Collection<Long> eventIds);
}
