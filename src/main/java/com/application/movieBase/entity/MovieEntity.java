package com.application.movieBase.entity;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "movie")
@AllArgsConstructor
@NoArgsConstructor
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @NotEmpty
    @NotBlank
    private String title;

    @NotEmpty
    @NotBlank
    @Size(max = 75)
    private String director;

    @Size(max = 300)
    @NotEmpty
    @NotBlank
    private String synopsis;

    @NotNull
    private Integer runtimeInSec;

    @NotBlank
    @Size(max = 50)
    private String countryOfOrigin;

    @NotEmpty
    @NotBlank
    @Past
    private Date releaseDate;
}
