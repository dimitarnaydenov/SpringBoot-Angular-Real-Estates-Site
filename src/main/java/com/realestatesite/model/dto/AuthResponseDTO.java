package com.realestatesite.model.dto;

public class AuthResponseDTO {
    private String username;
    private String accessToken;
    private String role;

    public AuthResponseDTO() { }

    public AuthResponseDTO(String username, String accessToken, String role) {
        this.username = username;
        this.accessToken = accessToken;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
