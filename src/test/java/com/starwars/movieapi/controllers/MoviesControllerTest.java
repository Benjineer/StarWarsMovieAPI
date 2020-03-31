/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starwars.movieapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starwars.movieapi.dtos.CommentDTO;
import com.starwars.movieapi.dtos.MovieDTO;
import com.starwars.movieapi.entities.Comment;
import com.starwars.movieapi.entities.Movie;
import com.starwars.movieapi.entities.MovieCharacter;
import com.starwars.movieapi.services.MovieService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author Oke
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class MoviesControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private MovieService movieService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private Movie movie;
    
    private String movieName;
    
    private String openingCrawls;
    
    private LocalDate releaseDate;
    
    private CommentDTO commentDTO;
    
    private Comment comment;
    
    private MovieCharacter movieCharacter;
    
    private MovieDTO movieDTO;
    
    private String movieListJson;
    
    private Map<String, Object> hashmap;
    
    private String characterListJson;
    
    private String commentListJson;
    
    private String commentDTOJson;
    
    private String commentDTO2Json;
    
    private CommentDTO commentDTO2;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws JsonProcessingException {
        
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
        
        commentDTO2 = new CommentDTO();
        commentDTO2.setId(1L);
        commentDTO2.setMovieId(1L);
        commentDTO2.setComment("It is a period of civil war.\\r\\nRebel spaceships, striking\\r\\nfrom a hidden base, have won\\r\\ntheir first victory against\\r\\nthe evil Galactic Empire.\\r\\n\\r\\nDuring the battle, Rebel\\r\\nspies managed to steal secret\\r\\nplans to the Empire's\\r\\nultimate weapon, the DEATH\\r\\nSTAR, an armored space\\r\\nstation with enough power\\r\\nto destroy an entire planet.\\r\\n\\r\\nPursued by the Empire's\\r\\nsinister agents, Princess\\r\\nLeia races home aboard her\\r\\nstarship, custodian of the\\r\\nstolen plans that can save her\\r\\npeople and restore\\r\\nfreedom to the galaxy....It is a dark time for the\\r\\nRebellion. Although the Death\\r\\nStar has been destroyed,\\r\\nImperial troops have driven the\\r\\nRebel forces from their hidden\\r\\nbase and pursued them across\\r\\nthe galaxy.\\r\\n\\r\\nEvading the dreaded Imperial\\r\\nStarfleet, a group of freedom\\r\\nfighters led by Luke Skywalker\\r\\nhas established a new secret\\r\\nbase on the remote ice world\\r\\nof Hoth.\\r\\n\\r\\nThe evil lord Darth Vader,\\r\\nobsessed with finding young\\r\\nSkywalker, has dispatched\\r\\nthousands of remote probes into\\r\\nthe far reaches of space....");
        commentDTO2.setIpAddress("0:0:0:0:0:0:0:1");
        
        
        comment = new Comment();
        comment.setId(1L);
        comment.setComment("this is a comment");
        comment.setDateTime(LocalDateTime.now());
        comment.setIpAddress("0:0:0:0:0:0:0:1");
        comment.setMovie(movie);
        
        movie.setComments(Arrays.asList(comment));
        
        movieCharacter = new MovieCharacter();
        movieCharacter.setId(1L);
        movieCharacter.setName("Luke Skywalker");
        movieCharacter.setHeight(170);
        movieCharacter.setGender("male");
        
        movie.setCharacters(Arrays.asList(movieCharacter));
        
        movieDTO = new MovieDTO();
        movieDTO.setId(1L);
        movieDTO.setName(movieName);
        movieDTO.setOpeningCrawls(openingCrawls);
        movieDTO.setReleaseDate(releaseDate.format(DateTimeFormatter.ISO_DATE));
        movieDTO.setCommentCount(1);
        
        hashmap = new HashMap<>();
        hashmap.put("results", Arrays.asList(movieCharacter));
        hashmap.put("total_no_of_characters", 1);
        hashmap.put("total_height_in_cm", "170cm");
        hashmap.put("total_height_in_ft_in", "5fts 6.93 inches");
        
        movieListJson = objectMapper.writeValueAsString(Arrays.asList(movieDTO));
        characterListJson = objectMapper.writeValueAsString(hashmap);
        commentListJson = objectMapper.writeValueAsString(Arrays.asList(commentDTO));
        commentDTOJson = objectMapper.writeValueAsString(commentDTO);
        commentDTO2Json = objectMapper.writeValueAsString(commentDTO2);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of list method, of class MoviesController.
     * @throws java.lang.Exception
     */
    @Test
    public void testListMovies() throws Exception {
        System.out.println("list movies");
        
        when(movieService.getMovies()).thenReturn(Arrays.asList(movieDTO));
        
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().json(movieListJson));
    }

    /**
     * Test of getCharacters method, of class MoviesController.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetMovieCharacters() throws Exception {
        System.out.println("get movie characters");
        
        when(movieService.getMovieCharacters(anyLong(), anyString(), anyString(), anyString())).thenReturn(hashmap);
        
        mockMvc.perform(get("/movies/1/characters"))
                .andExpect(status().isOk())
                .andExpect(content().json(characterListJson));
    }
    
    /**
     * Test of getCharacters method, of class MoviesController.
     * testGetMovieCharactersByNonExistingMovieId
     * @throws java.lang.Exception
     */
    @Test
    public void testGetMovieCharactersByNonExistingMovieId() throws Exception {
        System.out.println("get movie characters by non existing movie Id");
        
        when(movieService.getMovieCharacters(anyLong(), anyString(), anyString(), anyString())).thenReturn(new HashMap<>());
        
        mockMvc.perform(get("/movies/1/characters"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test of post method, of class MoviesController.
     * @throws java.lang.Exception
     */
    @Test
    public void testPostComments() throws Exception {
        System.out.println("post comment");
        
        when(movieService.addComment(any(CommentDTO.class))).thenReturn(Optional.of(1L));
        
        mockMvc.perform(post("/movies/1/comments").contentType(MediaType.APPLICATION_JSON).content(commentDTOJson))
                .andExpect(status().isOk())
                .andExpect(content().json("1"));
    }
    
    /**
     * Test of post method, of class MoviesController.
     * testPostCommentsGreaterThan500Characters
     * @throws java.lang.Exception
     */
    @Test
    public void testPostCommentsGreaterThan500Characters() throws Exception {
        System.out.println("post comments greater than 500 characters");
        
        mockMvc.perform(post("/movies/1/comments").contentType(MediaType.APPLICATION_JSON).content(commentDTO2Json))
                .andExpect(status().isBadRequest());
        
        verify(movieService, never()).addComment(any(CommentDTO.class));
    }
    
    /**
     * Test of post method, of class MoviesController.
     * testPostCommentsByNonExistingMovieId
     * @throws java.lang.Exception
     */
    @Test
    public void testPostCommentsByNonExistingMovieId() throws Exception {
        System.out.println("test post comments by non existing movie Id");
        
        when(movieService.addComment(any(CommentDTO.class))).thenReturn(Optional.empty());
        
        mockMvc.perform(post("/movies/1/comments").contentType(MediaType.APPLICATION_JSON).content(commentDTOJson))
                .andExpect(status().isNotFound());
    }

    /**
     * Test of getComments method, of class MoviesController.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetComments() throws Exception {
        System.out.println("get comments");
        
        when(movieService.getMovieComments(anyLong())).thenReturn(Arrays.asList(commentDTO));
        
        mockMvc.perform(get("/movies/1/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(commentListJson));
    }

    /**
     * Test of handleError method, of class MoviesController.
     * @throws java.lang.Exception
     */
    @Test
    public void testHandleError() throws Exception {
        
        System.out.println("handle error");
        
        mockMvc.perform(get("/error"))
                .andExpect(status().isInternalServerError());
    }
    
}
