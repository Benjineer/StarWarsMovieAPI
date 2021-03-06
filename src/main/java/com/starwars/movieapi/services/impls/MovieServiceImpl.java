/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starwars.movieapi.services.impls;

import com.starwars.movieapi.dtos.CommentDTO;
import com.starwars.movieapi.dtos.MovieCharacterDTO;
import com.starwars.movieapi.dtos.MovieDTO;
import com.starwars.movieapi.entities.Comment;
import com.starwars.movieapi.entities.Movie;
import com.starwars.movieapi.entities.MovieCharacter;
import com.starwars.movieapi.repositories.CommentRepository;
import com.starwars.movieapi.repositories.MovieCharacterRepository;
import com.starwars.movieapi.repositories.MovieRepository;
import com.starwars.movieapi.services.MovieService;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MovieCharacterRepository mcr;

    @Override
    public List<MovieDTO> getMovies() {

        List<MovieDTO> movieList = new ArrayList();
        List<Movie> movies = movieRepository.findAll(Sort.by(Sort.Direction.ASC, "releaseDate"));
        movies.forEach((movie) -> {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setId(movie.getId());
            movieDTO.setName(movie.getName());
            movieDTO.setOpeningCrawls(movie.getOpeningCrawls());
            movieDTO.setReleaseDate(movie.getReleaseDate().format(DateTimeFormatter.ISO_DATE));
            movieDTO.setCommentCount(movie.getComments().size());

            movieList.add(movieDTO);
        });

        return movieList;
    }

    @Override
    public Optional<Long> addComment(CommentDTO commentDTO) {

        Optional<Movie> optMovie = movieRepository.findById(commentDTO.getMovieId());

        if (optMovie.isPresent()) {
            Movie movie = optMovie.get();

            Comment comment = new Comment();
            comment.setComment(commentDTO.getComment());
            comment.setDateTime(LocalDateTime.now());
            comment.setIpAddress(commentDTO.getIpAddress());
            comment.setMovie(movie);

            comment = commentRepository.save(comment);

            return Optional.of(comment.getId());
        }

        return Optional.empty();
    }

    @Override
    public Map<String, Object> getMovieCharacters(Long id, String gender, String sortParam, String sortDirection) {

        Optional<Movie> optMovie = movieRepository.findById(id);

        Map<String, Object> hashMap = new HashMap<>();
        if (optMovie.isPresent()) {
            Movie movie = optMovie.get();

            List<MovieCharacter> movieChars = new ArrayList<>();

            if (!sortParam.isEmpty() && !sortDirection.isEmpty()) {
                
                movieChars = mcr.findByMovie(movie, Sort.by(Sort.Direction.fromString(sortDirection), sortParam));

            }else{
                movieChars = mcr.findByMovie(movie);
            }
            

            int totalCharacters = movieChars.size();
            int totalHeightInCM = 0;

            List<MovieCharacterDTO> mcdtos = new ArrayList<>();
            for (MovieCharacter movieChar : movieChars) {
                if(!gender.isEmpty() && !movieChar.getGender().equals(gender)){
                    continue;
                }
                int height = movieChar.getHeight();
                totalHeightInCM += height;

                MovieCharacterDTO mcdto = new MovieCharacterDTO();
                mcdto.setName(movieChar.getName());
                mcdto.setHeight(movieChar.getHeight());
                mcdto.setMass(movieChar.getMass());
                mcdto.setHairColor(movieChar.getHairColor());
                mcdto.setSkinColor(movieChar.getSkinColor());
                mcdto.setEyeColor(movieChar.getEyeColor());
                mcdto.setBirthYear(movieChar.getBirthYear());
                mcdto.setGender(movieChar.getGender());

                mcdtos.add(mcdto);
            }

            double feets = totalHeightInCM / 30.48;
            int fts = (int) feets;

            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.UP);

            double inches = 12 * (feets - fts);
            String incs = df.format(inches);

            hashMap.put("results", mcdtos);
            hashMap.put("total_no_of_characters", totalCharacters);
            hashMap.put("total_height_in_cm", totalHeightInCM + "cm");
            hashMap.put("total_height_in_ft_in", fts + "fts " + incs + " inches");
        }

        return hashMap;
    }

    @Override
    public List<CommentDTO> getMovieComments(Long id) {

        Optional<Movie> optMovie = movieRepository.findById(id);
        List<CommentDTO> cdtos = new ArrayList<>();
        optMovie.ifPresent((movie) -> {
            commentRepository.findByMovie(movie, Sort.by(Sort.Direction.DESC, "dateTime")).forEach((comment) -> {
                CommentDTO cdto = new CommentDTO();
                cdto.setId(comment.getId());
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
