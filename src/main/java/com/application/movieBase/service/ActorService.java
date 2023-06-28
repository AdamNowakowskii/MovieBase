package com.application.movieBase.service;

import static com.application.movieBase.utilities.CollectionMapper.mapEntitiesToDTOs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.application.movieBase.entity.ActorEntity;
import com.application.movieBase.repository.ActorRepository;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Service
public class ActorService {
    private final ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<ActorDTO> findAllActor(Pageable pagingParameters) {
        return mapEntitiesToDTOs(actorRepository.findAll(pagingParameters), ActorMapper::mapToDTO);
    }

    private static class ActorMapper {
        public static ActorDTO mapToDTO(ActorEntity actor) {
            return new ActorDTO(
                    actor.getFirstName(),
                    actor.getLastName()
            );
        }
    }

    @Getter
    public static class ActorDTO {
        private final String firstName;
        private final String lastName;

        @JsonCreator
        public ActorDTO(@JsonProperty("firstName") String firstName,
                        @JsonProperty("lastName") String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}

