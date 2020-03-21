package com.starwars.movieapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class StarWarsMovieApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarWarsMovieApiApplication.class, args);
    }
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
    
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}
