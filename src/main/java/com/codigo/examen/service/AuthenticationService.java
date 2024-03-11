package com.codigo.examen.service;

import com.codigo.examen.entity.UsuarioEntity;
import com.codigo.examen.request.UsuarioRequest;
import com.codigo.examen.request.SignInRequest;
import com.codigo.examen.response.AuthenticationResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<UsuarioEntity> createUsuario(UsuarioRequest usuarioRequest);

    AuthenticationResponse signin(SignInRequest iniciarSesionRequest);


}
