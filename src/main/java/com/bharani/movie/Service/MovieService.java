package com.bharani.movie.Service;

import com.bharani.movie.dto.MovieDto;
import com.bharani.movie.dto.PageResponse;
import com.bharani.movie.entity.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    MovieDto save(MovieDto dto, MultipartFile file) throws IOException;

    MovieDto get(Integer id);

    List<MovieDto> getAll();

    MovieDto update(Integer id,MovieDto dto,MultipartFile file) throws IOException;

    String delete(Integer id) throws IOException;

    PageResponse getAllWithPagination(Integer pageNumber,Integer pageSize);

    PageResponse getAllWithPaginationWithSorting(Integer pageNumber,Integer pageSize,String sortBy,String dir);
}
