package com.alura.foro.domain.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioDTO {


    private Long id;
    private String nombre;
    private String perfil;

    public UsuarioDTO(Long id, String nombre, String perfil) {
        this.id = id;
        this.nombre = nombre;
        this.perfil = perfil;
    }
}
