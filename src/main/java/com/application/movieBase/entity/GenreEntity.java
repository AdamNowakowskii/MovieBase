package com.application.movieBase.entity;

import javax.validation.constraints.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "genre")
@Data
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Genre genre;

    public enum Genre {
        ACTION,
        COMEDY,
        DRAMA,
        ROMANCE,
        HORROR,
        SCIENCE_FICTION,
        THRILLER,
        ANIMATION,
        ADVENTURE,
        CRIME
    }
}
