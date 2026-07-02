package com.example.ApplicationService.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileStorageService {
    public String saveFile(
            MultipartFile file)
            throws Exception {

        String uploadDir = "uploads";

        File directory =
                new File(uploadDir);

        if(!directory.exists()) {

            directory.mkdirs();
        }

        String fileName =
                System.currentTimeMillis()
                        + "_"
                        + file.getOriginalFilename();

        Path path =
                Paths.get(
                        uploadDir,
                        fileName
                );

        Files.write(
                path,
                file.getBytes()
        );

        return path.toString();
    }
}
