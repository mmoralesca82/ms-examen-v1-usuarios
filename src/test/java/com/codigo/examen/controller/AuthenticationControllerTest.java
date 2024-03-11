package com.codigo.examen.controller;

import com.codigo.examen.entity.UsuarioEntity;
import com.codigo.examen.request.UsuarioRequest;
import com.codigo.examen.service.impl.AuthenticationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

   @Mock
    private AuthenticationServiceImpl authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioEntity usuario;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

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

    }

//    @Test
//    void createUsuario_SuccessfulCreation_ReturnsOk() {
//        // Arrange
//        UsuarioRequest usuarioRequest = new UsuarioRequest();
//
//        when(authenticationService.createUsuario(usuarioRequest)).thenReturn(ResponseEntity.ok().build());
//
//        // Act
//        ResultActions resultActions = mockMvc.perform(post("//ms-examen/v1/autenticacion")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(usuario)));
//
//        ResponseEntity<?> response = authenticationService.createUsuario(usuarioRequest);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
}