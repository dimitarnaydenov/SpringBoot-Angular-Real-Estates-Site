package com.realestatesite.controller;

import com.realestatesite.model.Property;
import com.realestatesite.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @GetMapping("/all")
    public List<Property> getAllProperties() {
        return (List<Property>) propertyService.findAll();
    }

    @PostMapping("/addProperty")
    public ResponseEntity<Property> addProperty(@ModelAttribute Property property) {
        Property _property = propertyService.addProperty(property);
        return new ResponseEntity<>(_property, HttpStatus.CREATED);
    }
}
