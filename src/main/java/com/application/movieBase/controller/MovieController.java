package com.application.movieBase.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.movieBase.service.ActorService.ActorDTO;
import com.application.movieBase.service.MovieService;
import com.application.movieBase.service.MovieService.MovieDTO;
import com.application.movieBase.service.MovieService.MovieIDSearchDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(value = "/find/all", method = GET,
            produces = APPLICATION_JSON_VALUE)
    public List<MovieDTO> findAll(Pageable pagingParameters) {
        return movieService.findAllMovies(pagingParameters);
    }

    @RequestMapping(value = "/find", method = POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public List<MovieDTO> findMovies(
            @RequestBody MovieDTO movie,
            Pageable pagingParameters) {
        return movieService.findMoviesByCriteria(movie, pagingParameters);
    }

    @RequestMapping(value = "/find/{id}", method = GET,
            produces = APPLICATION_JSON_VALUE)
    public MovieDTO findById(@PathVariable Long id) {
        return movieService.findMovieByID(id).orElse(null);
    }

    @RequestMapping(value = "/delete/{id}", method = DELETE)
    public void deleteById(@PathVariable Long id) {
        movieService.deleteMovieByID(id);
    }

    @RequestMapping(value = "/find/actor", method = POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public List<MovieDTO> findByActor(
            @RequestBody ActorDTO actorDTO,
            Pageable pagingParameters) {
        return movieService.findByParticipatingActor(actorDTO, pagingParameters);
    }

    @RequestMapping(value = "/save", method = POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public MovieDTO save(@RequestBody MovieDTO movie) {
        return movieService.saveMovie(movie);
    }

    @RequestMapping(value = "find/id", method = POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public List<MovieIDSearchDTO> findMovieID(
            @RequestBody MovieDTO movie,
            Pageable pagingParameters) {
        return movieService.findMovieID(
                movie,
                pagingParameters);
    }

    @RequestMapping(value = "/modify/{id}", method = PUT,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public MovieDTO modify(
            @PathVariable Long id,
            @RequestBody MovieDTO movie) throws IllegalAccessException {
        return movieService.modifyMovie(id, movie);
    }

    @RequestMapping(value = "/find/time", method = POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public List<MovieDTO> findByTimeSpan(
            @RequestBody DateRange range,
            Pageable pagingParameters) {
        return movieService.findInTimeSpan(
                range.start, range.end,
                pagingParameters);
    }

    @Getter
    private static class DateRange {
        @JsonFormat(pattern = "yyyy-MM-dd")
        private final Date start;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private final Date end;

        @JsonCreator
        private DateRange(
                @JsonProperty("start") Date start,
                @JsonProperty("end") Date end) {
            this.start = start;
            this.end = end;
        }
    }
}
