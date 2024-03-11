package com.codigo.examen.service.impl;

import com.codigo.examen.entity.UsuarioEntity;
import com.codigo.examen.repository.UsuarioRepository;
import com.codigo.examen.request.SignInRequest;
import com.codigo.examen.request.UsuarioRequest;
import com.codigo.examen.response.AuthenticationResponse;
import com.codigo.examen.service.AuthenticationService;
import com.codigo.examen.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl  implements AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UsuarioServiceImpl usuarioService;
    @Override
    public ResponseEntity<UsuarioEntity> createUsuario(UsuarioRequest usuarioRequest) {
        Optional<UsuarioEntity> existingUser = usuarioRepository.findByUsername(usuarioRequest.getUsername());
        if (existingUser.isPresent() || usuarioRequest.getUsername() == null ||
                usuarioRequest.getPassword()== null || usuarioRequest.getEmail()== null ||
                usuarioRequest.getTelefono()== null) {
            return ResponseEntity.badRequest().body(null);
        }
        return usuarioService.getUsuarioResponseEntity(0L, usuarioRequest, "create");
    }

    @Override
    public AuthenticationResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),signInRequest.getPassword()));
        var user = usuarioRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no valido"));

        var jwt = jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse =  new AuthenticationResponse();
        authenticationResponse.setToken(jwt);
        return authenticationResponse;
    }
}
