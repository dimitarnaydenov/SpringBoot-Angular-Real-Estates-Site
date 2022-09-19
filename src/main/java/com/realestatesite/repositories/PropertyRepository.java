package com.realestatesite.repositories;

import com.realestatesite.model.Property;
import com.realestatesite.services.PropertyService;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends CrudRepository<Property, Integer> {

}
