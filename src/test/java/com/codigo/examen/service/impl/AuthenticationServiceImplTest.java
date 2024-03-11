package com.codigo.examen.service.impl;

import com.codigo.examen.entity.RolEntity;
import com.codigo.examen.entity.UsuarioEntity;
import com.codigo.examen.repository.UsuarioRepository;
import com.codigo.examen.request.UsuarioRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioServiceImpl usuarioService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private UsuarioEntity usuario;
    private UsuarioEntity usuarioNull;

    private UsuarioRequest usuarioRequest;

    private RolEntity roleUser;
    private RolEntity roleAdmin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        roleAdmin = RolEntity.builder()
                .idRol(1L)
                .nombreRol("ADMIN")
                .build();

        roleUser = RolEntity.builder()
                .idRol(2L)
                .nombreRol("USER")
                .build();

        usuario = UsuarioEntity.builder()
                .idUsuario(1L)
                .username("mmoralesca")
                .password("password")
                .email("email")
                .telefono("908938473")
                .roles(null)
                .enabled(true)
                .accountnonexpire(true)
                .accountnonlocked(true)
                .credentialsnonexpired(true)
                .build();

        usuarioRequest = UsuarioRequest.builder()
                .username(null)
                .password(null)
                .email(null)
                .telefono(null)
                .roles(null)
                .build();


        usuarioNull = UsuarioEntity.builder()
                .idUsuario(null)
                .username(null)
                .password(null)
                .email(null)
                .telefono(null)
                .roles(null)
                .build();
    }


//    @Test
//    void createUsuario_NewUser_Success() {
//        // Arrange
//        Set<String> rolString = new HashSet<>();
//        rolString.add("USER");
//        usuarioRequest.setUsername("usuario2");
//        usuarioRequest.setPassword("password");
//        usuarioRequest.setEmail("micorreo@micorreo.com");
//        usuarioRequest.setTelefono("87829894");
//        usuarioRequest.setRoles(rolString);
//        when(usuarioRepository.findByUsername("username")).thenReturn(Optional.empty());
//
//        ResponseEntity<UsuarioEntity> responseEntity = authenticationService.createUsuario(usuarioRequest);
//
//        assertEquals(200, responseEntity.getStatusCodeValue());
//        // Aquí puedes verificar más detalles sobre el cuerpo de la respuesta si es necesario
//    }

}