/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starwars.movieapi.services.impls;

import com.starwars.movieapi.entities.Movie;
import com.starwars.movieapi.entities.MovieCharacter;
import com.starwars.movieapi.repositories.MovieCharacterRepository;
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

    @Autowired
    private MovieCharacterRepository mcr;

    @Async
    @Scheduled(cron = "${cronexpression}")
    public void getMovies() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.ALL));
        headers.set("User-Agent", "curl/7.54.0");

        HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<Map> exchange = restTemplate.exchange(swapiUrl, HttpMethod.GET, httpEntity, Map.class);

        List<Map<String, Object>> results = new ArrayList<>();
        if (exchange.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> response = exchange.getBody();

            results = (List<Map<String, Object>>) response.getOrDefault("results", results);
            results.forEach((result) -> {
                String name = result.getOrDefault("title", "").toString();
                System.out.println(name);
                Optional<Movie> optMovie = movieRepository.findByName(name);
                if (!optMovie.isPresent()) {
                    Movie movie = new Movie();
                    movie.setName(name);
                    movie.setOpeningCrawls(result.getOrDefault("opening_crawl", "").toString());
                    movie.setReleaseDate(LocalDate.parse(result.getOrDefault("release_date", "").toString()));
                    
                    movieRepository.save(movie);

                    List<String> characters = (List<String>) result.getOrDefault("characters", new ArrayList<>());
                    
                    List<MovieCharacter> movieCharacters = new ArrayList<>();
                    
                    for (String characterUrl : characters) {
                        System.out.println(characterUrl);

                        ResponseEntity<Map> respEntity = restTemplate.exchange(characterUrl, HttpMethod.GET, httpEntity, Map.class);

                        if (respEntity.getStatusCode().is2xxSuccessful()) {

                            Map<String, Object> body = respEntity.getBody();

                            String charName = body.getOrDefault("name", "").toString();

                            MovieCharacter movieCharacter = new MovieCharacter();
                            movieCharacter.setName(charName);
                            movieCharacter.setHeight(Integer.parseInt(body.getOrDefault("height", "").toString()));
                            movieCharacter.setMass(body.getOrDefault("mass", "").toString());
                            movieCharacter.setHairColor(body.getOrDefault("hair_color", "").toString());
                            movieCharacter.setSkinColor(body.getOrDefault("skin", "").toString());
                            movieCharacter.setEyeColor(body.getOrDefault("eye_color", "").toString());
                            movieCharacter.setBirthYear(body.getOrDefault("birth_year", "").toString());
                            movieCharacter.setGender(body.getOrDefault("gender", "").toString());
                            movieCharacter.setMovie(movie);
                            
                            mcr.save(movieCharacter);

                        }
                    }

                }

            });
        }
    }

}
