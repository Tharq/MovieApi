package com.bharani.movie.Service;

import com.bharani.movie.dto.MovieDto;
import com.bharani.movie.dto.PageResponse;
import com.bharani.movie.entity.Movie;
import com.bharani.movie.exceptions.FileNotFoundException;
import com.bharani.movie.exceptions.MovieNotFoundException;
import com.bharani.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository repository;

    private final FileServiceImpl service;

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String BASE_URL;

    @Override
    public MovieDto save(MovieDto dto, MultipartFile file) throws IOException {

        if(file==null) throw new FileNotFoundException("Please provide valid file");

        String filename = service.uploadFile(path,file);

        Movie movie = Movie.builder()
                .movieId(null)
                .title(dto.getTitle())
                .director(dto.getDirector())
                .caste(dto.getCaste())
                .poster(filename)
                .studio(dto.getStudio())
                .year(dto.getYear())
                .build();
        repository.save(movie);

        String url = BASE_URL + "/file/" + filename;

        return MovieDto.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .director(movie.getDirector())
                .caste(movie.getCaste())
                .poster(movie.getPoster())
                .studio(movie.getStudio())
                .year(movie.getYear())
                .posterUrl(url)
                .build();
    }

    @Override
    public MovieDto get(Integer id) {

        Movie movie = repository.findById(id).orElseThrow(()-> new MovieNotFoundException("movie not found"));


        String url = BASE_URL + "/file/" + movie.getPoster();

        return MovieDto.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .director(movie.getDirector())
                .caste(movie.getCaste())
                .poster(movie.getPoster())
                .studio(movie.getStudio())
                .year(movie.getYear())
                .posterUrl(url)
                .build();
    }

    @Override
    public List<MovieDto> getAll() {

        List<Movie> movies = repository.findAll();
        List<MovieDto> dto = new ArrayList<>();

        for(Movie movie: movies){
            String url = BASE_URL + "/file/" + movie.getPoster();

            MovieDto m = MovieDto.builder()
                    .movieId(movie.getMovieId())
                    .title(movie.getTitle())
                    .director(movie.getDirector())
                    .caste(movie.getCaste())
                    .poster(movie.getPoster())
                    .studio(movie.getStudio())
                    .year(movie.getYear())
                    .posterUrl(url)
                    .build();
            dto.add(m);
        }
        return dto;
    }

    @Override
    public MovieDto update(Integer id, MovieDto dto, MultipartFile file) throws IOException {

        Movie movie = repository.findById(id).orElseThrow(()->new MovieNotFoundException("movie not found"));

        String fileName = movie.getPoster();

        if(file!=null){
            Files.deleteIfExists(Paths.get(path+ File.pathSeparator+fileName));
             fileName = service.uploadFile(path,file);
        }

        dto.setPoster(fileName);

        Movie m = Movie.builder()
                .movieId(movie.getMovieId())
                .title(dto.getTitle())
                .director(dto.getDirector())
                .caste(dto.getCaste())
                .poster(dto.getPoster())
                .studio(dto.getStudio())
                .year(dto.getYear())
                .build();
        Movie updatedMovie = repository.save(m);

        String url = BASE_URL+"/file/"+updatedMovie.getPoster();
        MovieDto response = MovieDto.builder()
                .movieId(updatedMovie.getMovieId())
                .title(updatedMovie.getTitle())
                .director(updatedMovie.getDirector())
                .caste(updatedMovie.getCaste())
                .poster(updatedMovie.getPoster())
                .studio(updatedMovie.getStudio())
                .year(updatedMovie.getYear())
                .posterUrl(url)
                .build();


        return response;
    }

    @Override
    public String delete(Integer id) throws IOException {
        Movie movie = repository.findById(id).orElseThrow(()-> new MovieNotFoundException("movie not found"));

        Files.deleteIfExists(Paths.get(path + File.pathSeparator + movie.getPoster()));

        repository.deleteById(id);

        return "Movie Deleted SuccessFully";
    }

    @Override
    public PageResponse getAllWithPagination(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        Page<Movie> page = repository.findAll(pageable);

        List<Movie> movies = page.getContent();
        List<MovieDto> dto = new ArrayList<>();

        for(Movie movie: movies){
            String url = BASE_URL + "/file/" + movie.getPoster();

            MovieDto m = MovieDto.builder()
                    .movieId(movie.getMovieId())
                    .title(movie.getTitle())
                    .director(movie.getDirector())
                    .caste(movie.getCaste())
                    .poster(movie.getPoster())
                    .studio(movie.getStudio())
                    .year(movie.getYear())
                    .posterUrl(url)
                    .build();
            dto.add(m);
        }

        return new PageResponse(dto,pageNumber,pageSize,page.getTotalElements(),page.getTotalPages(),page.isLast());
    }

    @Override
    public PageResponse getAllWithPaginationWithSorting
            (Integer pageNumber, Integer pageSize, String sortBy, String dir) {

        Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Movie> page = repository.findAll(pageable);

        List<Movie> movies = page.getContent();
        List<MovieDto> dto = new ArrayList<>();

        for(Movie movie: movies){
            String url = BASE_URL + "/file/" + movie.getPoster();

            MovieDto m = MovieDto.builder()
                    .movieId(movie.getMovieId())
                    .title(movie.getTitle())
                    .director(movie.getDirector())
                    .caste(movie.getCaste())
                    .poster(movie.getPoster())
                    .studio(movie.getStudio())
                    .year(movie.getYear())
                    .posterUrl(url)
                    .build();
            dto.add(m);
        }

        return new PageResponse(dto,pageNumber,pageSize,page.getTotalElements(),page.getTotalPages(),page.isLast());
    }
}
