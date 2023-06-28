package com.application.movieBase.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.application.movieBase.entity.ActorEntity;

public interface ActorRepository extends PagingAndSortingRepository<ActorEntity, Long> {
    Page<ActorEntity> findByFirstNameAndLastName(String firstName, String lastName, Pageable pagingParameters);
}
