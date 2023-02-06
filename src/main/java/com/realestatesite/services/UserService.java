package com.realestatesite.services;

import com.realestatesite.model.CustomUser;
import com.realestatesite.model.Role;
import com.realestatesite.model.dto.RegisterDTO;
import com.realestatesite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleService roleService;
    PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails loadUserByUsername(String username) {
        CustomUser user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username:" + username));
        return User.withUsername(user.getUsername()).password(user.getPassword()).authorities(user.getAuthorities()).build();
    }

    public CustomUser getUser(String username){

        Optional<CustomUser> user = userRepository.findByUsername(username);

        if(user.isPresent()) return user.get();

        return null;
    }

    @Transactional
    public void addUser(RegisterDTO registerDTO){

        CustomUser user = new CustomUser();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        if(userRepository.findAll().size() == 0) user.getRoles().add(roleService.getRole("ROLE_ADMIN"));
        else{
            user.addRole(roleService.getRole("ROLE_USER"));
        }

        userRepository.save(user);
    }

    public boolean existsByUsername(String username){
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean existsByEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

}
