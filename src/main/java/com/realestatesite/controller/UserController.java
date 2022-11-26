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
//@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokenUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO){

        if(userService.existsByUsername(registerDTO.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        CustomUser user = new CustomUser();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        if(roleService.getRole("ROLE_USER") == null)
        {
            roleService.addRole(new Role("ROLE_USER"));
        }
        user.addRole(roleService.getRole("ROLE_USER"));
        userService.addUser(user);

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
