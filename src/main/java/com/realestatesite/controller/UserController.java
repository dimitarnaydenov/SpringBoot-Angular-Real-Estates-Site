package com.realestatesite.controller;

import com.realestatesite.configuration.JwtTokenUtil;
import com.realestatesite.model.CustomUser;
import com.realestatesite.model.Role;
import com.realestatesite.model.dto.AuthRequestDTO;
import com.realestatesite.model.dto.AuthResponseDTO;
import com.realestatesite.model.dto.RegisterDTO;
import com.realestatesite.services.RoleService;
import com.realestatesite.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    UserService userService;
    RoleService roleService;
    AuthenticationManager authManager;
    JwtTokenUtil jwtUtil;


    @Autowired
    public UserController(UserService userService, RoleService roleService,
                          AuthenticationManager authManager, JwtTokenUtil jwtUtil) {
        this.userService = userService;
        this.roleService = roleService;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO){

        if(userService.existsByUsername(registerDTO.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if(userService.existsByEmail(registerDTO.getEmail())){
            return new ResponseEntity<>("Email already exists!", HttpStatus.BAD_REQUEST);
        }

        userService.addUser(registerDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            CustomUser user = (CustomUser) userService.getUser(userDetails.getUsername());
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponseDTO response = new AuthResponseDTO(user.getUsername(), accessToken, user.getRoles().stream().findFirst().get().getName());

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
