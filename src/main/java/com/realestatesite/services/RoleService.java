package com.realestatesite.services;

import com.realestatesite.model.Role;
import com.realestatesite.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {


    private RoleRepository roleRepository;
    
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRole(String name){
        return roleRepository.findRoleByName(name);
    }

    public void addRole(Role role){
        roleRepository.save(role);
    }
}
