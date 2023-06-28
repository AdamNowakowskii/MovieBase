package com.application.movieBase.repository;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.application.movieBase.entity.MovieEntity;

@Repository
public interface MovieRepository extends PagingAndSortingRepository<MovieEntity, Long> {
    Page<MovieEntity> findAll(Example<MovieEntity> entity, Pageable pagingParameters);

    Page<MovieEntity> findAllByIdIn(Set<Long> ids, Pageable pagingParameters);

    Page<MovieEntity> findByReleaseDateBetween(Date start, Date end, Pageable pagingParameters);

    void deleteById(Long id);

    Optional<MovieEntity> findById(Long id);

    Optional<MovieEntity> save(MovieEntity entity);
}
