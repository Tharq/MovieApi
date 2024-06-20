package com.bharani.movie.Service;

import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();

        String filePath = path + File.pathSeparator + fileName;

        File f = new File(path);

        if(!f.exists()){
            f.mkdir();
        }
        if(Files.exists(Paths.get(path+File.pathSeparator+file.getOriginalFilename()))){
            throw new FileAlreadyExistsException("file already exists");
        }
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String name) throws FileNotFoundException {

        String file = path + File.pathSeparator + name;
        return new FileInputStream(file);
    }
}
