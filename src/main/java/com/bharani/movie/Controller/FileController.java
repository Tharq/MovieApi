package com.bharani.movie.Controller;

import com.bharani.movie.Service.FileServiceImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {

    private final FileServiceImpl service;

    @Value("${project.poster}")
    private String path;

    @PostMapping("/upload")

    public ResponseEntity<String> upload(MultipartFile file) throws IOException {
        return ResponseEntity.ok(service.uploadFile(path,file));
    }

    @GetMapping("/{fileName}")

    public void serve(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        InputStream stream = service.getResourceFile(path,fileName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(stream,response.getOutputStream());
    }
}
