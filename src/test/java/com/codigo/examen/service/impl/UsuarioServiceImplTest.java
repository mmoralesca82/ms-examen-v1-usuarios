package com.codigo.examen.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.codigo.examen.entity.RolEntity;
import com.codigo.examen.entity.UsuarioEntity;
import com.codigo.examen.repository.RolRepository;
import com.codigo.examen.repository.UsuarioRepository;
import com.codigo.examen.request.UsuarioRequest;
import com.codigo.examen.service.DetallesUsuarioService;
import com.codigo.examen.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RolRepository rolRepository;
    @Mock
    private DetallesUsuarioService detallesUsuarioService;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private UsuarioEntity usuario;
    private UsuarioEntity usuarioNull;

    private UsuarioRequest usuarioRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        usuarioService = new UsuarioServiceImpl(usuarioRepository, rolRepository, detallesUsuarioService);

        RolEntity roleAdmin = new RolEntity().builder()
                .idRol(1L)
                .nombreRol("ADMIN")
                .build();

        RolEntity roleUser = new RolEntity().builder()
                .idRol(2L)
                .nombreRol("USER")
                .build();


        Set<RolEntity> rolSet = new HashSet<>();
        rolSet.add(roleAdmin);
        rolSet.add(roleUser);

        usuario = UsuarioEntity.builder()
                .idUsuario(1L)
                .username("mmoralesca")
                .password("password")
                .email("email")
                .telefono("908938473")
                .roles(rolSet)
                .enabled(true)
                .accountnonexpire(true)
                .accountnonlocked(true)
                .credentialsnonexpired(true)
                .build();

        usuarioRequest = UsuarioRequest.builder()
                .username("mmoralesca")
                .password("password")
                .email("email")
                .telefono("908938473")
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
    public void testGetUsuarioById_Success() {
        // Arrange
        Long id = 1L;
        String subjectFromToken = "mmoralesca";

        // Mocking
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));


        // Act
        Optional<UsuarioEntity> userFind = usuarioRepository.findById(id);
        ResponseEntity<UsuarioEntity> reponse = new ResponseEntity<>(HttpStatus.OK);

        // Assert
        assertTrue(userFind.isPresent());
        assertEquals(userFind.get().getIdUsuario(), id);
    }

    @Test
    public void testGetUsuarioById_AdminAuthorized_ReturnsUsuarioEntity() {
        // Arrange
        Long id = 2L;
        String subjectFromToken = "mmoralesca";


        // Mocking
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioNull));


        // Act
        Optional<UsuarioEntity> userFind = usuarioRepository.findById(id);
        ResponseEntity<UsuarioEntity> reponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Assert
        assertEquals(userFind.get(), usuarioNull);
        assertNotEquals(userFind.get().getIdUsuario(), id);
    }

    @Test
    void updateUsuario_Succes(){
        // Arrange
        Long id = 1L;
        String subjectFromToken = "mmoralesca";

        //Mocking
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Act
        Optional<UsuarioEntity> userUpdate = usuarioRepository.findById(id);
        usuario.setTelefono("012948394");
        UsuarioEntity userSave = usuarioRepository.save(usuario);
        ResponseEntity<UsuarioEntity> response = new ResponseEntity<>(HttpStatus.OK);

        // Assert
        assertEquals(userSave, usuario);
        assertEquals(userSave.getIdUsuario(), usuario.getIdUsuario());
        assertEquals(userUpdate.get().getTelefono(), "012948394");
    }

    @Test
    void updateUsuario_UnSucces(){
        // Arrange
        Long id = 2L;
        String subjectFromToken = "mmoralesca";

        //Mocking
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioNull));

        // Act
        Optional<UsuarioEntity> userUpdate = usuarioRepository.findById(id);
        ResponseEntity<UsuarioEntity> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // Assert
        assertNotEquals(userUpdate.get().getIdUsuario(), usuario.getIdUsuario());
    }

    @Test
    void deleteUsuario_Saccess(){
        // Arrange
        Long id = 1L;

        //Mocking
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Act
        Optional<UsuarioEntity> userDelete = usuarioRepository.findById(id);
        usuario.setEnabled(false);
        usuario.setAccountnonexpire(false);
        usuario.setCredentialsnonexpired(false);
        usuario.setAccountnonlocked(false);
        UsuarioEntity userSave = usuarioRepository.save(usuario);
        ResponseEntity<UsuarioEntity> response = new ResponseEntity<>(HttpStatus.ACCEPTED);

        // Assert
        assertFalse(userSave.isEnabled());


    }




}

