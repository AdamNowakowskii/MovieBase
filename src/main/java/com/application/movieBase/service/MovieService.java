package com.application.movieBase.service;

import static com.application.movieBase.service.ActorService.ActorDTO;
import static com.application.movieBase.utilities.CollectionMapper.mapEntitiesToDTOs;
import static com.application.movieBase.utilities.EntityUtilities.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.movieBase.entity.MovieEntity;
import com.application.movieBase.repository.ActorRepository;
import com.application.movieBase.repository.MovieRepository;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    @Autowired
    public MovieService(
            MovieRepository movieRepository,
            ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    public Optional<MovieDTO> findMovieByID(Long id) {
        return movieRepository.findById(id).map(MovieMapper::mapToDTO);
    }

    public MovieDTO saveMovie(MovieDTO movie) {
        Optional<MovieEntity> savedMovie = movieRepository
                .save(MovieMapper.mapToEntity(movie));

        if (!savedMovie.isPresent())
            return null;

        return MovieMapper.mapToDTO(savedMovie.get());
    }

    public List<MovieDTO> findAllMovies(Pageable pagingParameters) {
        Page<MovieEntity> result =
                movieRepository.findAll(pagingParameters);
        return mapEntitiesToDTOs(result, MovieMapper::mapToDTO);
    }

    public List<MovieDTO> findMoviesByCriteria(
            MovieDTO movie,
            Pageable pagingParameters) {
        Example<MovieEntity> criteria = Example.of(MovieMapper.mapToEntity(movie));
        Page<MovieEntity> result = movieRepository.findAll(criteria, pagingParameters);
        return mapEntitiesToDTOs(result, MovieMapper::mapToDTO);
    }

    public List<MovieIDSearchDTO> findMovieID(
            MovieDTO movie,
            Pageable pagingParameters) {
        Example<MovieEntity> criteria = Example.of(MovieMapper.mapToEntity(movie));
        Page<MovieEntity> result = movieRepository.findAll(criteria, pagingParameters);
        return mapEntitiesToDTOs(result, MovieMapper::mapToMovieIDSearchDTO);
    }

    public void deleteMovieByID(Long id) {
        movieRepository.deleteById(id);
    }

    @Transactional
    public MovieDTO modifyMovie(Long id, MovieDTO movie) throws IllegalAccessException {
        Optional<MovieEntity> movieEntity = movieRepository.findById(id);

        if (!movieEntity.isPresent())
            return null;

        return MovieMapper.mapToDTO(
                updateFromDTO(movieEntity.get(), movie));
    }

    public List<MovieDTO> findByParticipatingActor(
            ActorDTO actor,
            Pageable pagingParameters) {

        String firstName = actor.getFirstName();
        String lastName = actor.getLastName();

        Set<Long> movieIDs = actorRepository
                .findByFirstNameAndLastName(firstName, lastName, pagingParameters)
                .stream()
                .map(e -> e.getMovie().getId())
                .collect(Collectors.toSet());

        Page<MovieEntity> result = movieRepository.findAllByIdIn(movieIDs , pagingParameters);
        return mapEntitiesToDTOs(result, MovieMapper::mapToDTO);
    }

    public List<MovieDTO> findInTimeSpan(
            Date start, Date end,
            Pageable pagingParameters) {
        Page<MovieEntity> result = movieRepository.findByReleaseDateBetween(start, end, pagingParameters);
        return mapEntitiesToDTOs(result, MovieMapper::mapToDTO);
    }

    private static class MovieMapper {
        static MovieDTO mapToDTO(MovieEntity entity) {
            return new MovieDTO(
                    entity.getTitle(),
                    entity.getDirector(),
                    entity.getSynopsis(),
                    entity.getRuntimeInSec(),
                    entity.getCountryOfOrigin(),
                    entity.getReleaseDate());
        }

        static MovieIDSearchDTO mapToMovieIDSearchDTO(MovieEntity entity) {
            return new MovieIDSearchDTO(
                    entity.getId(),
                    entity.getTitle(),
                    entity.getDirector(),
                    entity.getCountryOfOrigin(),
                    entity.getReleaseDate());
        }

        static MovieEntity mapToEntity(MovieDTO movie) {
            return new MovieEntity(
                    null,
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getSynopsis(),
                    movie.getRuntimeInSec(),
                    movie.getCountryOfOrigin(),
                    movie.getReleaseDate());
        }

        static MovieEntity mapToEntity(Long id, MovieDTO movie) {
            return new MovieEntity(
                    id,
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getSynopsis(),
                    movie.getRuntimeInSec(),
                    movie.getCountryOfOrigin(),
                    movie.getReleaseDate());
        }
    }

    @Getter
    public static class MovieDTO {
        private final String title;
        private final String director;
        private final String synopsis;
        private final Integer runtimeInSec;
        private final String countryOfOrigin;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private final Date releaseDate;

        @JsonCreator
        public MovieDTO(
                @JsonProperty("title") String title,
                @JsonProperty("director") String director,
                @JsonProperty("synopsis") String synopsis,
                @JsonProperty("runtimeInSec") Integer runtimeInSec,
                @JsonProperty("countryOfOrigin") String countryOfOrigin,
                @JsonProperty("releaseDate") Date releaseDate) {
            this.title = title;
            this.director = director;
            this.synopsis = synopsis;
            this.runtimeInSec = runtimeInSec;
            this.countryOfOrigin = countryOfOrigin;
            this.releaseDate = releaseDate;
        }
    }

    @Getter
    public static class MovieIDSearchDTO {
        private final Long id;
        private final String title;
        private final String director;
        private final String countryOfOrigin;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private final Date releaseDate;

        @JsonCreator
        public MovieIDSearchDTO(
                @JsonProperty("id") Long id,
                @JsonProperty("title") String title,
                @JsonProperty("director") String director,
                @JsonProperty("countryOfOrigin") String countryOfOrigin,
                @JsonProperty("releaseDate") Date releaseDate) {
            this.id = id;
            this.title = title;
            this.director = director;
            this.countryOfOrigin = countryOfOrigin;
            this.releaseDate = releaseDate;
        }
    }
}
