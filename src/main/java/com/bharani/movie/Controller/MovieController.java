package com.bharani.movie.Controller;

import com.bharani.movie.Service.MovieService;
import com.bharani.movie.dto.MovieDto;
import com.bharani.movie.dto.PageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/movie")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {

    private final MovieService service;

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add-movie")
    public ResponseEntity<MovieDto> add(@RequestPart MultipartFile file,
                        @RequestPart String movieDto
                        ) throws IOException {

        MovieDto dto = converter(movieDto);

        return ResponseEntity.ok(service.save(dto,file));

    }

    private MovieDto converter(String dto) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(dto,MovieDto.class);
    }

    @GetMapping("/{id}")

    public MovieDto find(@PathVariable Integer id){
        return service.get(id);
    }

    @GetMapping("/movies")

    public List<MovieDto> get(){
        return service.getAll();
    }


    @DeleteMapping("/delete/{id}")

    public String delete(@PathVariable Integer id) throws IOException {
        return service.delete(id);
    }


    @PutMapping("/update/{id}")

    public MovieDto update(@PathVariable Integer id,
                           @RequestPart String dto,
                           @RequestPart MultipartFile file) throws IOException {
        if(file.isEmpty()) file=null;
        MovieDto d = converter(dto);
        return service.update(id,d,file);
    }
     @GetMapping("/page")
    public PageResponse getAllWithPagination
             (@RequestParam(defaultValue = "0",required = false) Integer pageNumber
                     ,@RequestParam(defaultValue = "1",required = false) Integer pageSize){
        return service.getAllWithPagination(pageNumber,pageSize);
     }

     @GetMapping("/page/sort")
     public PageResponse getAllWithPaginationWithSort
             (@RequestParam(defaultValue = "0",required = false) Integer pageNumber
                     ,@RequestParam(defaultValue = "1",required = false) Integer pageSize,
              @RequestParam (defaultValue = "movieId",required = false)String sortby,
              @RequestParam (defaultValue = "asc",required = false)String dir){
         return service.getAllWithPaginationWithSorting(pageNumber,pageSize,sortby,dir);
     }

}
