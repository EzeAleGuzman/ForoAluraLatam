package com.alura.foro.domain.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginDTO {
    private String nombre;
    private String contrasena;

    public UsuarioLoginDTO(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }
}
