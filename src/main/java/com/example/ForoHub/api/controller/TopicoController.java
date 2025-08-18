package com.example.ForoHub.api.controller;

import com.example.ForoHub.api.dto.DatosDetalleTopicoDTO;
import com.example.ForoHub.domain.curso.CursoRepository;
import com.example.ForoHub.domain.topico.DatosListaTopicoDTO;
import com.example.ForoHub.domain.topico.DatosRegistroTopicoDTO;
import com.example.ForoHub.domain.topico.Topico;
import com.example.ForoHub.domain.topico.TopicoRepository;
import com.example.ForoHub.domain.usuario.UsuarioRepository;
import com.example.ForoHub.infra.exceptions.DuplicateResourceException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> crear(@RequestBody @Valid DatosRegistroTopicoDTO dto,
                                   UriComponentsBuilder uriBuilder) {
        if (topicoRepository.existsByTituloAndMensaje(dto.titulo(), dto.mensaje())) {
            throw new DuplicateResourceException("El tópico ya existe con el mismo título y mensaje.");
        }
        var autor = usuarioRepository.findById(dto.autorId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no existe"));
        var curso = cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new EntityNotFoundException("Curso no existe"));

        var topico = new Topico(dto, autor, curso);
        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleTopicoDTO(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaTopicoDTO>> listarMedicos(
            @RequestParam(required=false) String curso,
            @RequestParam(required=false) Integer anio,
            @PageableDefault(size = 10, sort = {"fechaCreacion"}, direction = Sort.Direction.ASC) Pageable paginacion) {
        if (curso != null && anio != null) {
            var inicio = java.time.LocalDateTime.of(anio, 1, 1, 0, 0);
            var fin = inicio.withMonth(12).withDayOfMonth(31).withHour(23).withMinute(59).withSecond(59);
            var page = topicoRepository.findByCurso_NombreIgnoreCaseAndFechaCreacionBetween(curso, inicio, fin, paginacion)
                    .map(DatosListaTopicoDTO::new);
            return ResponseEntity.ok(page);
        }
        var page = topicoRepository.findAll(paginacion).map(DatosListaTopicoDTO::new);
        return ResponseEntity.ok(page);
    }
}
