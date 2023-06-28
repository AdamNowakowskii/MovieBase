package com.application.movieBase.controller;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.movieBase.service.ActorService;
import com.application.movieBase.service.ActorService.ActorDTO;

@RestController
public class ActorController {
    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @RequestMapping(value = "/actor/find/all",
            method = GET,
            produces = APPLICATION_JSON_VALUE)
    public List<ActorDTO> findAll(Pageable pagingParameters) {
        return actorService.findAllActor(pagingParameters);
    }
}
