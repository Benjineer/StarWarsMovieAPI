
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starwars.movieapi.services;

import com.starwars.movieapi.dtos.CommentDTO;
import com.starwars.movieapi.dtos.MovieDTO;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Oke
 */
public interface MovieService {
    
    List<MovieDTO> getMovies();
    
    Optional<Long> addComment(CommentDTO commentDTO);
    
    Map<String, Object> getMovieCharacters(Long id, String gender, String sortParam, String sortDirection);
    
    public List<CommentDTO> getMovieComments(Long id);
    
}
