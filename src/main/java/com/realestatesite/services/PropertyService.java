package com.realestatesite.services;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.realestatesite.model.Property;
import com.realestatesite.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService{

    @Autowired
    private PropertyRepository propertyRepository;

    public List<Property> findAll(){
        return (List<Property>) propertyRepository.findAll();
    }

    public Property addProperty(Property property){
        return propertyRepository.save(property);
    }

    public Property getPropertyById(int id){return propertyRepository.findById(id).get();}

    public void removePropertyById(int id){propertyRepository.deleteById(id);}

    public Property editPropertyById(int id, Property propertyDTO){
        Property property = propertyRepository.findById(id).get();

        if(propertyDTO.getName() != null){
            property.setName(propertyDTO.getName());
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

        return propertyRepository.save(property);
    }
}
