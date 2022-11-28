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

@RestController
@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/api/auth")
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @Autowired
    UserService userService;

    @Autowired
    FileRepository fileRepository;

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

        int i = 1;
        for(MultipartFile file : files){
            String fileName = "photo_" + i++ + '.' + file.getOriginalFilename().split("\\.")[1].toLowerCase();

            String uploadDir = "src/main/resources/static/property-pictures/" + _property.getId() +"/";

            try {
                _property.addPhoto(fileRepository.save(file,_property.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        propertyService.addProperty(_property);
        return new ResponseEntity<>(_property, HttpStatus.CREATED);
    }

    @PostMapping(path = "/test")
    public void test(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        String uploadDir = "user-photos/" + 1;

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
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

        fileRepository.deleteByPropertyId(propertyId);
        propertyService.removePropertyById(propertyId);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    private boolean checkIfIsAuthorOrAdmin(int propertyId, CustomUser customUser, Role role) {
        if (!role.getName().equals("ROLE_ADMIN") && !customUser.getId().equals(propertyService.getPropertyById(propertyId).getCustomUser().getId()))
            return true;
        return false;
    }

    private boolean checkFileExtension(@RequestParam("images[]") MultipartFile[] files) {
        for(MultipartFile file : files){
            String extension = file.getOriginalFilename().split("\\.")[1].toLowerCase();
            if(!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png")) return false;
        }
        return true;
    }
}
