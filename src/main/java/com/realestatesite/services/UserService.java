package com.realestatesite.services;

import com.realestatesite.model.CustomUser;
import com.realestatesite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) {
        CustomUser user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username:" + username));
        return User.withUsername(user.getUsername()).password(user.getPassword()).authorities(user.getAuthorities()).build();
    }

    public CustomUser getUser(String username){
        return userRepository.findByUsername(username).get();
    }

    @Transactional
    public void addUser(CustomUser customUser){userRepository.save(customUser);}

    public boolean existsByUsername(String username){
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean existsByEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public CustomUser findUserById(int id){
        return userRepository.findById(id).get();
    }
}
