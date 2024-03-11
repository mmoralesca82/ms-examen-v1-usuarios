package com.codigo.examen.service;

import com.codigo.examen.entity.UsuarioEntity;
import com.codigo.examen.request.UsuarioRequest;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {

    ResponseEntity<UsuarioEntity> getUsuarioById(Long id, String subjectFromToken);
    ResponseEntity<UsuarioEntity> updateUsuario(Long id, UsuarioRequest usuarioRequest, String subjectFromToken);
    ResponseEntity<UsuarioEntity> deleteUsuario(Long id);
    ResponseEntity<UsuarioEntity> getUsuarioResponseEntity(Long id, UsuarioRequest createUsuarioRequest, String typeRequest);
}
