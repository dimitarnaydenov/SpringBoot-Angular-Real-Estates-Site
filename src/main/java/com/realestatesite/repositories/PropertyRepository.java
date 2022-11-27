package com.realestatesite.repositories;

import com.realestatesite.model.Property;
import com.realestatesite.services.PropertyService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Integer> {
    List<Property> findByTypeContainsOrAddressContainsOrDescriptionContains(String type, String address, String description);
}
