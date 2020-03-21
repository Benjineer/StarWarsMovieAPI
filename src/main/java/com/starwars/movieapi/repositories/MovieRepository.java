/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starwars.movieapi.repositories;

import com.starwars.movieapi.entities.Movie;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Oke
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {
    
    Optional<Movie> findByName(String name);
    
}
