/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starwars.movieapi.repositories;

import com.starwars.movieapi.entities.Comment;
import com.starwars.movieapi.entities.Movie;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Oke
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findByMovie(Movie movie, Sort sort);
    
}
