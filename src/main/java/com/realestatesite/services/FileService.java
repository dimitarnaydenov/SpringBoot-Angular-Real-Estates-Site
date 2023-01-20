package com.realestatesite.services;

import com.realestatesite.model.Photo;
import com.realestatesite.model.Property;
import com.realestatesite.repositories.FileRepository;
import com.realestatesite.repositories.PhotoRepository;
import com.realestatesite.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService implements FileRepository {

    PhotoRepository photoRepository;
    PropertyRepository propertyRepository;

    @Autowired
    public FileService(PhotoRepository photoRepository, PropertyRepository propertyRepository) {
        this.photoRepository = photoRepository;
        this.propertyRepository = propertyRepository;
    }

    private final String root = "src/main/resources/static/property-pictures/";

    @Override
    public Photo save(MultipartFile file, Property property) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadDir = Paths.get(root + property.getId() +"/");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return photoRepository.save(new Photo( fileName,property));
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    @Override
    public void deleteByPropertyId(int propertyId) {
        Path deleteDir = Paths.get(root + propertyId +"/");
        FileSystemUtils.deleteRecursively(deleteDir.toFile());
    }

    @Override
    public void deleteByFileId(int fileId) {
        Photo photo = photoRepository.findById(fileId).get();
        Property property = photo.getProperty();
        File file = new File(root + photo.getProperty().getId() +"/" + photo.getUrl());
        if (file.delete()){
            property.deletePhoto(photo);
            propertyRepository.save(property);
            photoRepository.delete(photoRepository.findById(fileId).get());
        }
    }

    @Override
    public Resource load(String filename, int propertyId) {
        Path loadDir = Paths.get(root + propertyId +"/");
        try {
            Path file = loadDir.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
