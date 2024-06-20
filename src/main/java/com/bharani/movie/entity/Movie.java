package com.bharani.movie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(nullable = false)
    @NotBlank(message = "please provide movie name")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "please provide movie director")
    private String director;

    @Column(nullable = false)
    @NotBlank(message = "please provide movie studio")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movie_caste")
    private Set<String> caste;

    @Column(nullable = false)

    private Integer year;

    @Column(nullable = false)
    @NotBlank(message = "please provide movie poster")
    private String poster;
}
