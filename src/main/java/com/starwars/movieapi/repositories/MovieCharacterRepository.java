

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starwars.movieapi.repositories;

import com.starwars.movieapi.entities.Movie;
import com.starwars.movieapi.entities.MovieCharacter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Oke
 */
public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {
    
    Optional<MovieCharacter> findByName(String name);
    
    List<MovieCharacter> findByMovie(Movie movie);
    
    List<MovieCharacter> findByMovie(Movie movie, Sort sort);
    
    List<MovieCharacter> findByMovieAndGender(Movie movie,String gender);
    
    List<MovieCharacter> findByMovieAndGender(Movie movie, String gender, Sort sort);
    
}
