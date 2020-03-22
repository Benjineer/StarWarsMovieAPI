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
import com.starwars.movieapi.repositories.MovieCharacterRepository;
import com.starwars.movieapi.repositories.MovieRepository;
import com.starwars.movieapi.services.MovieService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Oke
 */
@RunWith(SpringRunner.class)
public class MovieServiceImplTest {
    
    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private CommentRepository commentRepository;
    
    @MockBean
    private MovieCharacterRepository mcr;
    
    @Autowired
    private MovieService movieService;
    
    private Movie movie;
    
    private String movieName;
    
    private String openingCrawls;
    
    private LocalDate releaseDate;
    
    private CommentDTO commentDTO;
    
    private Comment comment;
    
    private Comment comment1;
    
    private LocalDateTime commentDateTime;
    
    private MovieCharacter movieCharacter;
    
    public MovieServiceImplTest() {
    }
    
    @TestConfiguration
    static class MovieServiceImplTestContextConfiguration{
        
        @Bean
        public MovieServiceImpl movieServiceImpl(){
            return new MovieServiceImpl();
        }
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        movie = new Movie();
        movie.setId(1L);
        
        movieName = "A New Hope";
        movie.setName(movieName);
        
        openingCrawls = "It is a period of civil war. Rebel spaceships, striking from a hidden base, have won their first victory against the evil Galactic Empire.";
        movie.setOpeningCrawls(openingCrawls);
        
        releaseDate = LocalDate.now();
        movie.setReleaseDate(releaseDate);
        
        commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setMovieId(1L);
        commentDTO.setComment("this is a comment");
        commentDTO.setIpAddress("0:0:0:0:0:0:0:1");
        
        comment = new Comment();
        comment.setId(1L);
        comment.setComment("this is a comment");
        
        commentDateTime = LocalDateTime.now();
        comment.setDateTime(commentDateTime);
        comment.setIpAddress("0:0:0:0:0:0:0:1");
        comment.setMovie(movie);
        
        comment1 = new Comment();
        comment1.setComment("this is a comment");
        comment1.setDateTime(commentDateTime);
        comment1.setIpAddress("0:0:0:0:0:0:0:1");
        comment1.setMovie(movie);
        
        movie.setComments(Arrays.asList(comment));
        
        movieCharacter = new MovieCharacter();
        movieCharacter.setId(1L);
        movieCharacter.setName("Luke Skywalker");
        movieCharacter.setHeight(170);
        movieCharacter.setGender("male");
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getMovies method, of class MovieServiceImpl.
     */
    @Test
    public void testGetMovies() {
        System.out.println("getMovies");
        
        movie.setCharacters(Arrays.asList(movieCharacter));
        
        when(movieRepository.findAll(Sort.by(Sort.Direction.ASC, "releaseDate"))).thenReturn(Arrays.asList(movie));
        
        MovieDTO movieDTO;
        movieDTO = new MovieDTO();
        movieDTO.setId(1L);
        movieDTO.setName(movieName);
        movieDTO.setOpeningCrawls(openingCrawls);
        movieDTO.setReleaseDate(releaseDate.format(DateTimeFormatter.ISO_DATE));
        movieDTO.setCommentCount(1);
        
        List<MovieDTO> expResult = Arrays.asList(movieDTO);
        List<MovieDTO> result = movieService.getMovies();
        assertEquals(expResult.size(), result.size());
    }

    /**Todo Fix issue with test
     * Test of addComment method, of class MovieServiceImpl.
     */
//    @Test
//    public void testAddComment() {
//        System.out.println("addComment");
//        
//        when(movieRepository.findById(commentDTO.getMovieId())).thenReturn(Optional.of(new Movie()));
//        when(commentRepository.save(comment1)).thenReturn(comment);
//        
//        Optional<Long> expResult = Optional.of(1L);
//        Optional<Long> result = movieService.addComment(commentDTO);
//        assertEquals(expResult, result);
//    }
    
    @Test
    public void testAddCommentWhenMovieIsNotFound() {
        System.out.println("AddCommentWhenMovieIsNotFound");
        
        when(movieRepository.findById(commentDTO.getMovieId())).thenReturn(Optional.empty());
        Optional<Long> expResult = Optional.empty();
        Optional<Long> result = movieService.addComment(commentDTO);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMovieCharacters method, of class MovieServiceImpl.
     */
    @Test
    public void testGetMovieCharacters() {
        System.out.println("getMovieCharacters");
        Long id = 1L;
        String gender = "male";
        String sortParam = "";
        String sortDirection = "";
        
        Map<String, Object> expResult = new HashMap<>();
        expResult.put("results", Arrays.asList(movieCharacter));
        expResult.put("total_no_of_characters", 1);
        expResult.put("total_height_in_cm", "170cm");
        expResult.put("total_height_in_ft_in", "5fts 6.93 inches");
        
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(mcr.findByMovieAndGender(movie, gender)).thenReturn(Arrays.asList(movieCharacter));
        
        Map<String, Object> result = movieService.getMovieCharacters(id, gender, sortParam, sortDirection);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get("total_no_of_characters").toString(), result.get("total_no_of_characters").toString());
        assertEquals(expResult.get("total_height_in_cm").toString(), result.get("total_height_in_cm").toString());
        assertEquals(expResult.get("total_height_in_ft_in").toString(), result.get("total_height_in_ft_in").toString());
    }

    /**
     * Test of getMovieComments method, of class MovieServiceImpl.
     */
    @Test
    public void testGetMovieComments() {
        System.out.println("getMovieComments");
        
        Long id = 1L;
        
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        
        when(commentRepository.findByMovie(movie, Sort.by(Sort.Direction.DESC, "dateTime"))).thenReturn(Arrays.asList(comment));
        
        List<CommentDTO> expResult = Arrays.asList(commentDTO);
        List<CommentDTO> result = movieService.getMovieComments(id);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(0).getId(), result.get(0).getId());
        
    }
    
}
