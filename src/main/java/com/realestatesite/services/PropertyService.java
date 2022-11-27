package com.realestatesite.services;

import com.realestatesite.model.CustomUser;
import com.realestatesite.model.Property;
import com.realestatesite.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService{

    @Autowired
    private PropertyRepository propertyRepository;

    public Page<Property> findAll(Pageable paging){
        return propertyRepository.findAll(paging);
    }

    public Property addProperty(Property property){
        return propertyRepository.save(property);
    }

    public Property getPropertyById(int id){return propertyRepository.findById(id).get();}

    public void removePropertyById(int id){propertyRepository.deleteById(id);}

    public Property editPropertyById(int id, Property propertyDTO){
        Property property = propertyRepository.findById(id).get();

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

        return propertyRepository.save(property);
    }

    public List<Property> searchProperties(String search){
        return propertyRepository.findByTypeContainsOrAddressContainsOrDescriptionContains(search,search,search);
    }

    public List<Property> getPropertiesByCustomUser(CustomUser customUser){
        return  propertyRepository.findByCustomUser(customUser);
    }
}
