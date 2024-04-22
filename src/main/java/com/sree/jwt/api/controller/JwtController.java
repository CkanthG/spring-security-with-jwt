package com.sree.jwt.api.controller;

import com.sree.jwt.api.models.AuthRequest;
import com.sree.jwt.api.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This class exposes endpoints to use.
 */
@RestController
@RequestMapping("/jwt")
@Slf4j
public class JwtController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * This simple get request gives a string response.
     * @return string
     */
    @GetMapping("/")
    public String welcome() {
        return "Welcome to JWT";
    }

    /**
     * This method is accepting a bean and return JWT token.
     * @param authRequest
     * @return JWT token string
     */
    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception e) {
            log.error("Invalid Username/Password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }
}
