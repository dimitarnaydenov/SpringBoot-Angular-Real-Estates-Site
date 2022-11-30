package com.realestatesite.repositories;

import com.realestatesite.model.CustomUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<CustomUser, Integer> {

    Optional<CustomUser> findByUsername(String username);

    Optional<CustomUser> findByEmail(String email);
}
