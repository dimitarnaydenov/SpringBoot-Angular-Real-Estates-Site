package com.realestatesite.controller;

import com.realestatesite.model.Property;
import com.realestatesite.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/api/auth")
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @GetMapping("/all")
    @RolesAllowed("USER")
    public List<Property> getAllProperties() {
        return (List<Property>) propertyService.findAll();
    }

    @GetMapping("/test")
    @RolesAllowed("USER")
    public ResponseEntity<?> test() {
        return new ResponseEntity<>("text",HttpStatus.OK);
    }

    @PostMapping("/addProperty")
    public ResponseEntity<Property> addProperty(@RequestBody Property property) {
        Property _property = propertyService.addProperty(property);
        return new ResponseEntity<>(_property, HttpStatus.CREATED);
    }

    @GetMapping("/getProperty")
    public Property getPropertyById(@RequestParam String id) {
        return propertyService.getPropertyById(Integer.parseInt(id));
    }
}
