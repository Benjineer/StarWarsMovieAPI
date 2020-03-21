/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starwars.movieapi.services.impls;

import com.starwars.movieapi.dtos.CommentDTO;
import com.starwars.movieapi.dtos.MovieDTO;
import com.starwars.movieapi.entities.Comment;
import com.starwars.movieapi.entities.Movie;
import com.starwars.movieapi.entities.MovieCharacter;
import com.starwars.movieapi.repositories.CommentRepository;
import com.starwars.movieapi.repositories.MovieRepository;
import com.starwars.movieapi.services.MovieService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Oke
 */
@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<MovieDTO> getMovies() {

        List<MovieDTO> movieList = new ArrayList();
        List<Movie> movies = movieRepository.findAll(Sort.by(Sort.Direction.ASC, "releaseDate"));
        movies.forEach((movie) -> {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setName(movie.getName());
            movieDTO.setOpeningCrawls(movie.getOpeningCrawls());
            movieDTO.setReleaseDate(movie.getReleaseDate().format(DateTimeFormatter.ISO_DATE));
            movieDTO.setCommentCount(movie.getComments().size());

            movieList.add(movieDTO);
        });

        return movieList;
    }

    @Override
    public Long addComment(CommentDTO commentDTO) {

        Optional<Movie> optMovie = movieRepository.findById(commentDTO.getMovieId());

        if (optMovie.isPresent()) {
            Movie movie = optMovie.get();

            Comment comment = new Comment();
            comment.setComment(commentDTO.getComment());
            comment.setDateTime(LocalDateTime.now());
            comment.setIpAddress(commentDTO.getIpAddress());
            comment.setMovie(movie);
            
            Comment save = commentRepository.save(comment);
            
            return save.getId();
        }
        
        return null;

    }

    @Override
    public List<MovieCharacter> getMovieCharacters() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CommentDTO> getMovieComments(Long id) {
        
        Optional<Movie> optMovie = movieRepository.findById(id);
        List<CommentDTO> cdtos = new ArrayList<>();
        optMovie.ifPresent((movie) -> {
            commentRepository.findByMovie(movie, Sort.by(Sort.Direction.DESC, "dateTime")).forEach((comment) -> {
                CommentDTO cdto = new CommentDTO();
                cdto.setComment(comment.getComment());
                cdto.setDateTime(comment.getDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace('T', ' '));
                cdto.setIpAddress(comment.getIpAddress());
                cdto.setMovieId(id);
                
                cdtos.add(cdto);
            });
        });
        
        return cdtos;
    }

}
