package com.realestatesite.services;

import com.realestatesite.model.Property;
import com.realestatesite.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService{

    @Autowired
    PropertyRepository propertyRepository;

    public List<Property> findAll(){
        return (List<Property>) propertyRepository.findAll();
    }

    public Property addProperty(Property property){
        return propertyRepository.save(property);
    }
}
