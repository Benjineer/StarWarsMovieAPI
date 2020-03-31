package com.starwars.movieapi;

import com.starwars.movieapi.controllers.MoviesController;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StarWarsMovieApiApplicationTests {

    @Autowired
    private MoviesController moviesController;

    @Test
    void contextLoads() {
        assertThat(moviesController).isNotNull();
    }

}
