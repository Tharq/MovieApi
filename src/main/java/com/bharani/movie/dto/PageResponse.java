package com.bharani.movie.dto;

import java.util.List;

public record PageResponse(List<MovieDto> dtos,
                           Integer pageNumber,
                           Integer pageSize,
                           Long totalElements,
                           Integer totalPages,
                           Boolean isLast) {
}

