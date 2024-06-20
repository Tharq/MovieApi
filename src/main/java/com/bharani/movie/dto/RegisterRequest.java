package com.bharani.movie.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegisterRequest {

    private String name;

    private String userName;

    private String email;

    private String password;
}
