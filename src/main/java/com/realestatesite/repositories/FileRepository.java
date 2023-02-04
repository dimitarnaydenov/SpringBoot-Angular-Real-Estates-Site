package com.realestatesite.repositories;

import com.realestatesite.model.Photo;
import com.realestatesite.model.Property;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileRepository {
    Photo save(MultipartFile file, Property property) throws IOException;

    byte[] getPhoto(String filename);
}
