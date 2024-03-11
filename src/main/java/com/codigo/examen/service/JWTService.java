package com.codigo.examen.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

    String generateToken (UserDetails userDetails);

    boolean validateToken (String token, UserDetails userDetails);
    String extractUserName(String token);
}
