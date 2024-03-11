package com.codigo.examen.controller;

import com.codigo.examen.entity.UsuarioEntity;
import com.codigo.examen.request.UsuarioRequest;
import com.codigo.examen.service.JWTService;
import com.codigo.examen.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ms-examen/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private  final JWTService jwtService;


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> getUsuarioById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                        @PathVariable Long id) {
        String subjectFromToken = getSubjectFromToken(token);
        return usuarioService.getUsuarioById(id, subjectFromToken);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioEntity> updateUsuario(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id,
                                                       @RequestBody UsuarioRequest usuarioRequest) {
        String subjectFromToken = getSubjectFromToken(token);
        return usuarioService.updateUsuario(id,usuarioRequest, subjectFromToken);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        return usuarioService.deleteUsuario(id);
    }

    private String getSubjectFromToken(String token){
        final String jwt = token.substring(7);
        return jwtService.extractUserName(jwt);
    }
}
