package com.autocrud.main.dtos;

import java.util.List;

public class UserDTO {
    private String email;
    private List<String> roles;

    public UserDTO() {}

    public UserDTO(String email, List<String> roles) {
        this.email = email;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
