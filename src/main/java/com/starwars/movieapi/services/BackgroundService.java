/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starwars.movieapi.services;

import com.starwars.movieapi.entities.Movie;
import com.starwars.movieapi.repositories.MovieRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Oke
 */
@Service
public class BackgroundService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${swapiUrl}")
    private String swapiUrl;

    @Autowired
    private MovieRepository movieRepository;

    @Async
    @Scheduled(fixedDelay = 10000)
    public void getMovies() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.ALL));
        headers.set("User-Agent", "curl/7.54.0");

        HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<Map> exchange = restTemplate.exchange(swapiUrl, HttpMethod.GET, httpEntity, Map.class);
        
        List<Map<String, Object>> results = new ArrayList<>();
        HttpStatus statusCode = exchange.getStatusCode();
        
        if (statusCode.is2xxSuccessful()) {
            Map<String, Object> response = exchange.getBody();

            results = (List<Map<String, Object>>) response.getOrDefault("results", results);
            results.forEach((result) -> {
                String name = result.getOrDefault("title", "").toString();
                Optional<Movie> optMovie = movieRepository.findByName(name);
                if (!optMovie.isPresent()) {
                    Movie movie = new Movie();
                    movie.setName(name);
                    movie.setOpeningCrawls(result.getOrDefault("opening_crawl", "").toString());
                    movie.setReleaseDate(LocalDate.parse(result.getOrDefault("release_date", "").toString()));
                    List<String> characters = (List<String>) result.getOrDefault("characters", new ArrayList<>());
//                    movie.setCharacters(characters);
                    
                    movieRepository.save(movie);
                }

            });
        }
    }

}
