package com.example.gametopscores.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthRequest {

    // Getter and Setter for username
    private String username;
    // Getter and Setter for password
    private String password;

    // Default constructor
    public AuthRequest() {
    }

    // Parameterized constructor
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}

