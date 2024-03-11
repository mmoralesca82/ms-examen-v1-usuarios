package com.codigo.examen.service.impl;

import com.codigo.examen.repository.UsuarioRepository;
import com.codigo.examen.service.DetallesUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetallesUsuarioServiceImpl implements DetallesUsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return usuarioRepository.findByUsername(username).orElseThrow( () ->
                        new UsernameNotFoundException("Ususario no se encuentra en la base de datos."));
            }
        };
    }
}
