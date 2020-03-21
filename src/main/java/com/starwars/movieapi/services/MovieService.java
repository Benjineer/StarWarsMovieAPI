
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starwars.movieapi.services;

import com.starwars.movieapi.dtos.CommentDTO;
import com.starwars.movieapi.dtos.MovieDTO;
import com.starwars.movieapi.entities.MovieCharacter;
import java.util.List;

/**
 *
 * @author Oke
 */
public interface MovieService {
    
    List<MovieDTO> getMovies();
    
    Long addComment(CommentDTO commentDTO);
    
    List<MovieCharacter> getMovieCharacters();
    
    public List<CommentDTO> getMovieComments(Long id);
    
}
