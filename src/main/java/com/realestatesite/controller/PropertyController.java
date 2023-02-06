package com.realestatesite.controller;

import com.realestatesite.helpers.FileUploadUtil;
import com.realestatesite.model.CustomUser;
import com.realestatesite.model.Photo;
import com.realestatesite.model.Property;
import com.realestatesite.repositories.FileRepository;
import com.realestatesite.repositories.PhotoRepository;
import com.realestatesite.services.PropertyService;
import com.realestatesite.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.realestatesite.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/api/auth")
public class PropertyController {

    PropertyService propertyService;
    UserService userService;
    FileRepository fileRepository;

    @Autowired
    public PropertyController(PropertyService propertyService, UserService userService, FileRepository fileRepository) {
        this.propertyService = propertyService;
        this.userService = userService;
        this.fileRepository = fileRepository;
    }

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
    public ResponseEntity<Property> addProperty(@ModelAttribute Property property,  @RequestParam("images[]") MultipartFile[] files) throws IOException {

        if (!checkFileExtension(files)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        property.setCustomUser((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Property _property = propertyService.addProperty(property);

        for(MultipartFile file : files){
            try {
                _property.addPhoto(fileRepository.save(file,_property));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        propertyService.addProperty(_property);
        return new ResponseEntity<>(_property, HttpStatus.CREATED);
    }

    @GetMapping("/getProperty")
    public Property getPropertyById(@RequestParam String id) {
        return propertyService.getPropertyById(Integer.parseInt(id));
    }

    @GetMapping("/getUserProperties")
    public List<Property> getUserProperties() {
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return propertyService.getPropertiesByCustomUser(customUser);
    }

    @PostMapping("/editProperty")
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<Property> editPropertyById(@RequestParam String id, @ModelAttribute Property property,@RequestParam("photosToRemove[]") String[] photosId, @RequestParam("images[]") MultipartFile[] files) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser customUser = userService.getUser(principal.getUsername());

        if (!checkFileExtension(files)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        if (!checkIfIsAuthorOrAdmin(property.getId(), customUser, customUser.getRoles()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return new ResponseEntity<>(propertyService.editPropertyById(Integer.parseInt(id),property,photosId, files), HttpStatus.CREATED);
    }

    @DeleteMapping("/removeProperty")
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<?> removePropertyById(@RequestParam String id){
        int propertyId = Integer.parseInt(id);
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!checkIfIsAuthorOrAdmin(propertyId, customUser, customUser.getRoles()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        propertyService.removePropertyById(propertyId);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    private boolean checkIfIsAuthorOrAdmin(int propertyId, CustomUser customUser, List<Role> roles) {
        if (roles.stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN")) || customUser.getId().equals(propertyService.getPropertyById(propertyId).getCustomUser().getId()))
            return true;
        return false;
    }

    private boolean checkFileExtension(@RequestParam("images[]") MultipartFile[] files) {
        for(MultipartFile file : files){
            String extension = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1].toLowerCase();
            if(!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png")) return false;
        }
        return true;
    }
}
