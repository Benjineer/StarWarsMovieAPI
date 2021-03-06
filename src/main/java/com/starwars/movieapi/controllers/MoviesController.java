/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starwars.movieapi.controllers;

import com.starwars.movieapi.dtos.CommentDTO;
import com.starwars.movieapi.dtos.MovieDTO;
import com.starwars.movieapi.services.MovieService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Oke
 */
@RestController
@RequestMapping("/movies")
public class MoviesController {
    
    @Autowired
    private MovieService movieService;
    
    @GetMapping("")
    public ResponseEntity<List<MovieDTO>> list() {
        return ResponseEntity.ok(movieService.getMovies());
    }
    
    @GetMapping("/{id}/characters")
    public ResponseEntity<?> getCharacters(@PathVariable Long id, @RequestParam(required = false, defaultValue = "") String gender, @RequestParam(required = false, defaultValue = "") String sortParam, @RequestParam(required = false, defaultValue = "") String sortDirection) {
        Map<String, Object> movieCharacters = movieService.getMovieCharacters(id, gender, sortParam, sortDirection);
        if(movieCharacters.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movieCharacters);
    }
    
    @PostMapping("/{id}/comments")
    public ResponseEntity<?> post(@PathVariable Long id, @RequestBody CommentDTO commentDTO, HttpServletRequest request) {
        
        if(commentDTO.getComment().length() > 500){
            return ResponseEntity.badRequest().build();
        }
        
        commentDTO.setIpAddress(request.getRemoteAddr());
        commentDTO.setMovieId(id);
        
        Optional<Long> optLong = movieService.addComment(commentDTO);
        
        return ResponseEntity.of(optLong);
    }
    
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Long id) {
        List<CommentDTO> movieComments = movieService.getMovieComments(id);
        return ResponseEntity.ok(movieComments);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    
}
