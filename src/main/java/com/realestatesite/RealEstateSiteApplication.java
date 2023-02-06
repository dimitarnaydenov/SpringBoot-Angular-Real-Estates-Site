package com.realestatesite;

import com.realestatesite.model.CustomUser;
import com.realestatesite.model.Property;
import com.realestatesite.model.Role;
import com.realestatesite.repositories.PropertyRepository;
import com.realestatesite.repositories.RoleRepository;
import com.realestatesite.repositories.UserRepository;
import com.realestatesite.services.PropertyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class RealEstateSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealEstateSiteApplication.class, args);
	}

    @Bean
    public CommandLineRunner demoData(PropertyRepository propertyRepository, RoleRepository roleRepository) {
        return args -> {

            roleRepository.save(new Role("ROLE_ADMIN"));
            roleRepository.save(new Role("ROLE_USER"));
        };
    }

}
