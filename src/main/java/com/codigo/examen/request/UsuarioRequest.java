package com.codigo.examen.request;


import jakarta.persistence.Column;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    private Long idUsuario;
    private String username;
    private String password;
    private String email;
    private String telefono;
    Set<String> roles; //Los roles seran recibidos explicitamente (ADMIN, USER, SUPER, etc) y no por Id.
}
