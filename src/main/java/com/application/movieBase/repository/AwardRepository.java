package com.application.movieBase.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.application.movieBase.entity.AwardEntity;

@Repository
public interface AwardRepository extends CrudRepository<AwardEntity, Long> {
    List<AwardEntity> findByMovie_Id(Long id);

    List<AwardEntity> findByMovie_title(String title);
}
