package com.alura.foro.domain.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroUsuarioDTO {
    private String nombre;
    private String email;
    private String contrasena;
    private Perfil perfil;
}
