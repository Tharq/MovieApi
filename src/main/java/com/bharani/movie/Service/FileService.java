package com.bharani.movie.Service;

import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    String uploadFile(String path, MultipartFile file) throws IOException;

    InputStream getResourceFile(String path,String name) throws FileNotFoundException;
}
