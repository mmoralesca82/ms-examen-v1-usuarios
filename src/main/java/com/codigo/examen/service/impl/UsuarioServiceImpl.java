package com.codigo.examen.service.impl;

import com.codigo.examen.entity.RolEntity;
import com.codigo.examen.entity.UsuarioEntity;
import com.codigo.examen.repository.RolRepository;
import com.codigo.examen.repository.UsuarioRepository;
import com.codigo.examen.request.UsuarioRequest;
import com.codigo.examen.service.DetallesUsuarioService;
import com.codigo.examen.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final DetallesUsuarioService detallesUsuarioService;


    @Override
    public ResponseEntity<UsuarioEntity> getUsuarioById(Long id, String subjectFromToken) {
        //ADMIN puede obtener datos de todos, los demas roles solo de si mismos
        if(!authorizedHere(id, subjectFromToken)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        Optional<UsuarioEntity> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<UsuarioEntity> updateUsuario(Long id, UsuarioRequest usuarioRequest, String subjectFromToken) {
        //ADMIN puede modifciar datos de todos, los demas roles solo de si mismos.
        if(!authorizedHere(id, subjectFromToken)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        Optional<UsuarioEntity> existingUsuario = usuarioRepository.findById(id);
        if (existingUsuario.isPresent()) {
            if (usuarioRequest.getUsername() != null && // se agrega esta linea para permitir modificar otros campos que
                    //no sean el userName, como password por ejemplo.
                    !usuarioRequest.getUsername().equals(existingUsuario.get().getUsername())) {
                Optional<UsuarioEntity> userWithNewUsername = usuarioRepository.findByUsername(usuarioRequest.getUsername());
                if (userWithNewUsername.isPresent()) {
                    return ResponseEntity.badRequest().body(null);
                }
            }
            return getUsuarioResponseEntity(id, usuarioRequest, "update");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<UsuarioEntity> deleteUsuario(Long id) { // solo ADMIN podrá eliminar de manera lógica
        Optional<UsuarioEntity> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuario.get().setEnabled(false);
            usuario.get().setAccountnonlocked(false);
            usuario.get().setCredentialsnonexpired(false);
            usuario.get().setAccountnonexpire(false);
            usuarioRepository.save(usuario.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<UsuarioEntity> getUsuarioResponseEntity(Long id, UsuarioRequest usuarioRequest, String typeRequest) {
        Set<RolEntity> assignedRoles = new HashSet<>();
        UsuarioEntity usuario = new UsuarioEntity();
        if(usuarioRequest.getRoles() != null){
            for (String roles : usuarioRequest.getRoles()) { //Los roles vienen explícitos (ADMIN, USER, etc)
                Optional<RolEntity> rol = rolRepository.findByNombreRol(roles);
                //return ResponseEntity.badRequest().body(null);
                rol.ifPresent(assignedRoles::add);
            }
        }else if (typeRequest.equals("create")){
            assignedRoles.add(rolRepository.findByNombreRol("USER").orElse(null));
        }

        if(typeRequest.equals("update")){usuario=usuarioRepository.findById(id).get();}
        if(usuarioRequest.getUsername() != null){usuario.setUsername(usuarioRequest.getUsername());}
        if(usuarioRequest.getPassword() != null){usuario.setPassword(new BCryptPasswordEncoder()
                .encode(usuarioRequest.getPassword()));}
        if(usuarioRequest.getEmail() != null){usuario.setEmail(usuarioRequest.getEmail());}
        if(usuarioRequest.getTelefono() != null){usuario.setTelefono(usuarioRequest.getTelefono());}
        if(!assignedRoles.isEmpty()){usuario.setRoles(assignedRoles);} // Asignamos los roles.

        UsuarioEntity updatedUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(updatedUsuario);
    }


    public Boolean authorizedHere(Long id, String subjectFromToken){
        Set<RolEntity> roles = usuarioRepository.findByUsername(subjectFromToken).get().getRoles();
        for( RolEntity rol : roles){
            if(rol.getNombreRol().equals("ADMIN")) return true;
        }
        Long idSubject = usuarioRepository.findByUsername(subjectFromToken).get().getIdUsuario();
        if(Objects.equals(idSubject, id)) return true;

        return false;
    }



}
