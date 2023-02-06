package com.realestatesite.services;

import com.realestatesite.helpers.FileUploadUtil;
import com.realestatesite.model.Photo;
import com.realestatesite.model.Property;
import com.realestatesite.repositories.FileRepository;
import com.realestatesite.repositories.PhotoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService implements FileRepository {

    PhotoRepository photoRepository;

    @Autowired
    public FileService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    @Transactional
    public Photo save(MultipartFile file, Property property) throws IOException {
        String fileName = StringUtils.cleanPath(property.getId()+'_'+file.getOriginalFilename());

        return photoRepository.save(new Photo(fileName,property, FileUploadUtil.compressImage(file.getBytes())));

    }

    @Override
    public byte[] getPhoto(String filename)
    {
        return FileUploadUtil.decompressImage(photoRepository.getPhotoByUrl(filename).getData());
    }
}
