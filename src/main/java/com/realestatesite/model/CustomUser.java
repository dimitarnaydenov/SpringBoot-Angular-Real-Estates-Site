package com.realestatesite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class CustomUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique=true)
    private String username;
    @NotNull
    @Column(unique=true)
    private String email;
    @NotNull
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "customUser")
    @JsonIgnore
    private Collection<Property> properties = new ArrayList<>();

    public CustomUser() {
    }

    public CustomUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public Collection<Property> getProperties() {
        return properties;
    }

    public void setProperties(Collection<Property> properties) {
        this.properties = properties;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
