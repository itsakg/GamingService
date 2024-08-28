package com.example.gametopscores.controller;

import com.example.gametopscores.model.AuthRequest;
import com.example.gametopscores.service.CustomUserDetailsService;
import com.example.gametopscores.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public String createToken(@RequestBody AuthRequest authRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwtToken = jwtUtil.generateToken(userDetails);

        // Print the generated token to the console
        System.out.println("Generated JWT Token: " + jwtToken);
        return jwtToken;
    }
}
