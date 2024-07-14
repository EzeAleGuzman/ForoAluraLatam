package com.alura.foro.controller;

import com.alura.foro.domain.curso.Curso;
import com.alura.foro.domain.curso.CursoRepository;
import com.alura.foro.domain.topico.*;
import com.alura.foro.domain.usuario.Usuario;
import com.alura.foro.domain.usuario.UsuarioRepository;
import com.alura.foro.service.TopicoConverterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TopicoConverterService topicoConverterService;

    @GetMapping
    public  ResponseEntity<Page<DatosTopicoDTO>> getAllTopicos(@PageableDefault(page = 0, size = 5)Pageable pageable) {
           return ResponseEntity.ok(topicoConverterService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> crearTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico) {
        Usuario autor = usuarioRepository.findById(datosRegistroTopico.autor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado"));
        Curso curso = cursoRepository.findById(datosRegistroTopico.curso().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));

        Topico topico = new Topico();
        topico.setTitulo(datosRegistroTopico.titulo());
        topico.setMensaje(datosRegistroTopico.mensaje());
        topico.setFechadecreacion(LocalDateTime.now());
        topico.setAutor(autor);
        topico.setCurso(curso);
        topico.setStatus(true);
        topicoRepository.save(topico);

        DatosTopicoDTO datosTopicoDTO = topicoConverterService.convertToDTO(topico);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tópico creado exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT,"Tópico no encontrado"));

        Usuario autor = usuarioRepository.findById(datosActualizarTopico.autor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado"));
        Curso curso = cursoRepository.findById(datosActualizarTopico.curso().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));

        topico.setTitulo(datosActualizarTopico.titulo());
        topico.setMensaje(datosActualizarTopico.mensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);

        try {
            topicoRepository.save(topico);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El título del tópico ya existe");
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El título del tópico no existe en la base de datos");
        }

        DatosTopicoDTO datosTopicoDTO = topicoConverterService.convertToDTO(topico);
        return ResponseEntity.ok("Topico Editado" + datosTopicoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));

        topicoRepository.delete(topico);
        return ResponseEntity.ok("Topico: "+ topico.getTitulo() + "-*- Eliminado Correctamente");
    }


}








