package com.codewiz.eventbooking;

import com.codewiz.eventbooking.entity.Artist;
import com.codewiz.eventbooking.entity.EventArtist;
import com.codewiz.eventbooking.entity.Venue;
import com.codewiz.eventbooking.repository.ArtistRepository;
import com.codewiz.eventbooking.repository.EventArtistRepository;
import com.codewiz.eventbooking.repository.VenueRepository;
import org.dataloader.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.WebGraphQlInterceptor;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Configuration
public class DataLoaderConfig {

    private final VenueRepository venueRepository;
    private final ArtistRepository artistRepository;
    private final EventArtistRepository eventArtistRepository;

    public DataLoaderConfig(VenueRepository venueRepository,
                            ArtistRepository artistRepository,
                            EventArtistRepository eventArtistRepository) {
        this.venueRepository = venueRepository;
        this.artistRepository = artistRepository;
        this.eventArtistRepository = eventArtistRepository;
    }

    @Bean
    public WebGraphQlInterceptor dataLoaderInterceptor() {
        return (webInput, interceptorChain) -> {

            DataLoaderRegistry registry = new DataLoaderRegistry();

            registry.register("venueLoader",
                    DataLoaderFactory.newMappedDataLoader(venueBatchLoader()));

            registry.register("artistLoader",
                    DataLoaderFactory.newMappedDataLoader(artistBatchLoader()));

            webInput.configureExecutionInput((executionInput, builder) ->
                    builder.dataLoaderRegistry(registry).build()
            );

            return interceptorChain.next(webInput);
        };
    }

    private MappedBatchLoader<Long, Venue> venueBatchLoader() {
        return ids -> CompletableFuture.supplyAsync(() -> {

            List<Venue> venues = venueRepository.findByIdIn(ids);

            return venues.stream()
                    .collect(Collectors.toMap(Venue::id, v -> v));
        });
    }

    private MappedBatchLoader<Long, List<Artist>> artistBatchLoader() {
        return eventIds -> CompletableFuture.supplyAsync(() -> {

            List<EventArtist> relations =
                    eventArtistRepository.findByEventIdIn(eventIds);

            Set<Long> artistIds = relations.stream()
                    .map(EventArtist::artistId)
                    .collect(Collectors.toSet());

            List<Artist> artists = artistRepository.findByIdIn(artistIds);

            Map<Long, Artist> artistMap = artists.stream()
                    .collect(Collectors.toMap(Artist::id, a -> a));

            Map<Long, List<Artist>> result = new HashMap<>();

            for (Long eventId : eventIds) {

                List<Artist> list = relations.stream()
                        .filter(r -> r.eventId().equals(eventId))
                        .map(r -> artistMap.get(r.artistId()))
                        .toList();

                result.put(eventId, list);
            }

            return result;
        });
    }
}