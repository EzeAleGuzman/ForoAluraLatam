package com.alura.foro.domain.curso;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CursoDTO {

    String nombre;
    Categoria categoria;

    public CursoDTO(String nombre, Categoria categoria) {
        this.nombre = nombre;
        this.categoria = categoria;
    }


}
