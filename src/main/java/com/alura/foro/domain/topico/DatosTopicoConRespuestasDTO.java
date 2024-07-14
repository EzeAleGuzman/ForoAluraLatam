package com.alura.foro.domain.topico;

import com.alura.foro.domain.curso.Curso;
import com.alura.foro.domain.respuesta.Respuesta;
import com.alura.foro.domain.usuario.Usuario;
import com.alura.foro.domain.usuario.UsuarioDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DatosTopicoConRespuestasDTO {
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private UsuarioDTO autor;
    private boolean status;
    private Curso curso;
    private List<Respuesta> respuestas;

}