package com.codigo.examen.service.impl;

import com.codigo.examen.entity.RolEntity;
import com.codigo.examen.entity.UsuarioEntity;
import com.codigo.examen.repository.RolRepository;
import com.codigo.examen.repository.UsuarioRepository;
import com.codigo.examen.request.UsuarioRequest;
import com.codigo.examen.service.DetallesUsuarioService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private  UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private  DetallesUsuarioService detallesUsuarioService;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private UsuarioEntity usuario;
    private UsuarioEntity usuarioNull;

    private UsuarioRequest usuarioRequest;

    private RolEntity roleUser;
    private RolEntity roleAdmin;

    private Set<RolEntity> rolSet = new HashSet<>();

    @BeforeEach
    void setUp() {
            MockitoAnnotations.initMocks(this);
            usuarioService = new UsuarioServiceImpl(usuarioRepository, rolRepository, detallesUsuarioService);

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


    @Test
    void updateUsuario_Succes_Ass_ADMIN_and_OwnerData_Success(){
        // Arrange
        rolSet.clear();
        rolSet.add(roleAdmin);
        usuarioRequest.setTelefono("012948394"); //Dato a modificar
        Long id = 1L;
        String subjectFromToken = "mmoralesca";
        String typeRequest = "update";


        //Mocking
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);


        // Act
        Optional<UsuarioEntity> existingUsuario = usuarioRepository.findById(id);
        ResponseEntity<UsuarioEntity> response = usuarioService.getUsuarioResponseEntity(id,
                usuarioRequest,typeRequest);
        // Assert
        assertEquals("012948394",usuario.getTelefono());
        assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    void updateUsuario_Succes_As_ADMIN_and_NotOwnerData_Success(){
        // Arrange
        rolSet.clear();
        rolSet.add(roleAdmin);
        UsuarioEntity usuario2 = new UsuarioEntity();
        usuario2.setIdUsuario(2L);
        usuario2.setUsername("user2");
        usuario2.setEmail("corro@correo.com");
        usuarioRequest.setEmail("micorreo@correo.com"); //Dato a modificar
        Long id = 2L;
        String subjectFromToken = "mmoralesca";
        String typeRequest = "update";


        //Mocking
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario2));
        when(usuarioRepository.save(usuario2)).thenReturn(usuario2);


        // Act
        Optional<UsuarioEntity> existingUsuario = usuarioRepository.findById(id);
        ResponseEntity<UsuarioEntity> response = usuarioService.getUsuarioResponseEntity(id,
                usuarioRequest,typeRequest);
        // Assert
        assertEquals("micorreo@correo.com",usuario2.getEmail());
        assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    void updateUsuario_Succes_As_ADMIN_and_NoId_Exists(){
        // Arrange
        rolSet.add(roleAdmin);
        usuario.setRoles(rolSet);
        usuarioRequest.setEmail("micorreo@correo.com"); //Dato a modificar
        Long id = 2L;
        String subjectFromToken = "mmoralesca";
        String typeRequest = "update";

        //Mocking
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<UsuarioEntity> existingUsuario = usuarioRepository.findById(id);
        ResponseEntity response = ResponseEntity.status(400).body(null);

        // Assert
        assertEquals(existingUsuario,Optional.empty());
        assertEquals(400, response.getStatusCodeValue());
    }


    @Test
    void getUsuario_as_Admin_with_Id_Exists(){
        // Arrange
        rolSet.add(roleAdmin);
        usuario.setRoles(rolSet);
        Long id = 1L;
        String subjectFromToken = "mmoralesca";

        //Mocking
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));


        // Act
        Optional<UsuarioEntity> userFind = usuarioRepository.findById(id);
        ResponseEntity response = ResponseEntity.status(200).body(userFind);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(id, userFind.get().getIdUsuario());

    }


}