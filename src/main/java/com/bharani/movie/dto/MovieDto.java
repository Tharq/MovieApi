package com.bharani.movie.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {

    private Integer movieId;


    @NotBlank(message = "please provide movie name")
    private String title;


    @NotBlank(message = "please provide movie director")
    private String director;


    @NotBlank(message = "please provide movie studio")
    private String studio;


    private Set<String> caste;


    private Integer year;



    private String poster;

    @NotBlank(message = "please provide movie poster url")
    private String posterUrl;
}
