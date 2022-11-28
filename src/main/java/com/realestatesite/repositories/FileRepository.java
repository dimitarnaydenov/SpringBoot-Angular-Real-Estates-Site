package com.realestatesite.repositories;

import com.realestatesite.model.Photo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileRepository {
    Photo save(MultipartFile file, int propertyId) throws IOException;

    void deleteByPropertyId(int propertyId);

    Resource load(String filename, int propertyId);
}
