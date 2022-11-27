package com.realestatesite.controller;

import com.realestatesite.model.CustomUser;
import com.realestatesite.model.Property;
import com.realestatesite.repositories.PropertyRepository;
import com.realestatesite.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import com.realestatesite.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.security.RolesAllowed;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/api/auth")
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllProperties(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "6") int size) {
        Pageable paging = PageRequest.of(page, size);

        Page<Property> pageProperties = propertyService.findAll(paging);

        List<Property> properties = pageProperties.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("properties", properties);
        response.put("currentPage", pageProperties.getNumber());
        response.put("totalItems", pageProperties.getTotalElements());
        response.put("totalPages", pageProperties.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String search) {
        return new ResponseEntity<>(propertyService.searchProperties(search),HttpStatus.OK);
    }

    @PostMapping("/addProperty")
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<Property> addProperty(@RequestBody Property property) {
        property.setCustomUser((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Property _property = propertyService.addProperty(property);
        return new ResponseEntity<>(_property, HttpStatus.CREATED);
    }

    @GetMapping("/getProperty")
    public Property getPropertyById(@RequestParam String id) {
        return propertyService.getPropertyById(Integer.parseInt(id));
    }

    @PostMapping("/editProperty")
    @RolesAllowed("ROLE_ADMIN")
    public Property editPropertyById(@RequestParam String id, @RequestBody Property property) {
        return propertyService.editPropertyById(Integer.parseInt(id),property);
    }

    @DeleteMapping("/removeProperty")
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<?> removePropertyById(@RequestParam String id){
        int propertyId = Integer.parseInt(id);
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Role role = (Role)customUser.getRoles().toArray()[0];

        if (checkIfIsAuthorOrAdmin(propertyId, customUser, role))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        propertyService.removePropertyById(propertyId);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    private boolean checkIfIsAuthorOrAdmin(int propertyId, CustomUser customUser, Role role) {
        if (!role.getName().equals("ROLE_ADMIN") && !customUser.getId().equals(propertyService.getPropertyById(propertyId).getCustomUser().getId()))
            return true;
        return false;
    }
}
