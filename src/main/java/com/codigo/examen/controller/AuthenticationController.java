package com.codigo.examen.controller;

import com.codigo.examen.request.SignInRequest;
import com.codigo.examen.request.UsuarioRequest;
import com.codigo.examen.response.AuthenticationResponse;
import com.codigo.examen.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ms-examen/v1/autenticacion")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        return ResponseEntity.ok(authenticationService.createUsuario(usuarioRequest));
    }

    @PostMapping("/signin") // Logeo
    public ResponseEntity<AuthenticationResponse> signin(@RequestBody SignInRequest signinRequest){
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }

}
