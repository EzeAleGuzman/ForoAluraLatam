package com.alura.foro.domain.respuesta;

import com.alura.foro.domain.curso.Curso;

import java.util.Date;


public record DatosRespuestaTopico(Long id, String título, String mensaje,boolean solucion, Date fechaCreacion, Curso curso) {
}
