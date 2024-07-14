package com.alura.foro.domain.topico;

import com.alura.foro.domain.curso.Curso;
import com.alura.foro.domain.curso.CursoDTO;
import com.alura.foro.domain.usuario.Usuario;
import com.alura.foro.domain.usuario.UsuarioDTO;

import java.time.LocalDateTime;
import java.util.Date;


public record DatosTopicoDTO(Long id,
                             String titulo,
                             String mensaje,
                             LocalDateTime fechadecreacion,
                             UsuarioDTO autor,
                             boolean status,
                             CursoDTO curso
) {

}
