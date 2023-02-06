package com.realestatesite.services;

import com.realestatesite.model.CustomUser;
import com.realestatesite.model.Photo;
import com.realestatesite.model.Property;
import com.realestatesite.repositories.FileRepository;
import com.realestatesite.repositories.PhotoRepository;
import com.realestatesite.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService{


    private PropertyRepository propertyRepository;
    private FileRepository fileRepository;
    private PhotoRepository photoRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository, FileRepository fileRepository, PhotoRepository photoRepository) {
        this.propertyRepository = propertyRepository;
        this.fileRepository = fileRepository;
        this.photoRepository = photoRepository;
    }

    public Page<Property> findAll(Pageable paging){
        return propertyRepository.findAll(paging);
    }

    @Transactional
    public Property addProperty(Property property){
        return propertyRepository.save(property);
    }

    public Property getPropertyById(int id){
        Optional<Property> property = propertyRepository.findById(id);
        if(property.isPresent())
            return property.get();

        return null;
    }

    @Transactional
    public void removePropertyById(int id){propertyRepository.deleteById(id);}

    @Transactional
    public Property editPropertyById(int id, Property propertyDTO, String[] photosId, MultipartFile[] files){

        Property property = propertyRepository.findById(id).get();

        for(String photoId: photosId){

            Photo photo = photoRepository.findById(Integer.parseInt(photoId)).get();
            if(property.getPhotos().contains(photo)){
                property.getPhotos().remove(photo);
                photoRepository.delete(photo);
            }

        }

        if(propertyDTO.getType() != null){
            property.setType(propertyDTO.getType());
        }

        if(propertyDTO.getAddress() != null){
            property.setAddress(propertyDTO.getAddress());
        }

        if(propertyDTO.getDescription() != null){
            property.setDescription(propertyDTO.getDescription());
        }

        if(propertyDTO.getPrice() != null){
            property.setPrice(propertyDTO.getPrice());
        }

        if(propertyDTO.getPhoneNumber() != null){
            property.setPhoneNumber(propertyDTO.getPhoneNumber());
        }

        if(propertyDTO.getBeds() != property.getBeds()){
            property.setBeds(propertyDTO.getBeds());
        }

        if(propertyDTO.getBaths() != property.getBaths()){
            property.setBaths(propertyDTO.getBaths());
        }

        if(files.length > 0 && (property.getPhotos().size() + files.length <= 2)){
            for(MultipartFile file : files){
                try {
                    property.addPhoto(fileRepository.save(file,property));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return propertyRepository.save(property);
    }

    public List<Property> searchProperties(String search){
        return propertyRepository.findByTypeContainsOrAddressContainsOrDescriptionContains(search,search,search);
    }

    public List<Property> getPropertiesByCustomUser(CustomUser customUser){
        return  propertyRepository.findByCustomUser(customUser);
    }
}
