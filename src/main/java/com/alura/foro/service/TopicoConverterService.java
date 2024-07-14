package com.alura.foro.service;

import com.alura.foro.domain.curso.Curso;
import com.alura.foro.domain.curso.CursoDTO;
import com.alura.foro.domain.topico.DatosTopicoDTO;
import com.alura.foro.domain.topico.Topico;
import com.alura.foro.domain.topico.TopicoRepository;

import com.alura.foro.domain.usuario.Usuario;
import com.alura.foro.domain.usuario.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicoConverterService {

    @Autowired
    private TopicoRepository topicoRepository;

    public Page<DatosTopicoDTO> findAll(Pageable pageable) {
        return topicoRepository.findAll(pageable).map(this::convertToDTO);
    }

    public Optional<DatosTopicoDTO> findById(Long id) {
        return topicoRepository.findById(id).map(this::convertToDTO);
    }

    public Topico save(Topico topico) {
        return topicoRepository.save(topico);
    }

    public void deleteById(Long id) {
        topicoRepository.deleteById(id);
    }

    public DatosTopicoDTO convertToDTO(Topico topico) {
        return new DatosTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechadecreacion(),
                convertToUsuarioDTO(topico.getAutor()),
                true,
                convertToCursoDTO(topico.getCurso())
        );
    }

    public CursoDTO convertToCursoDTO(Curso curso) {
        return new CursoDTO(
                curso.getNombre(),
                curso.getCategoria()
        );}


    public UsuarioDTO convertToUsuarioDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getPerfil().toString()
        );
    }
}
