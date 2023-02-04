package com.realestatesite.controller;


import com.realestatesite.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FilesController {

    FileRepository fileRepository;

    @Autowired
    public FilesController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String filename) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(fileRepository.getPhoto(filename));
    }
}
