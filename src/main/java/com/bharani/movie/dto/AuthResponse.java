package com.bharani.movie.dto;

import com.bharani.movie.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class AuthResponse {

    private String accessToken;

    private String refreshToken;

    private Role role;
}
