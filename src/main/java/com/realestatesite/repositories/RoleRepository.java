package com.realestatesite.repositories;

import com.realestatesite.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role findRoleByName(String name);
}
