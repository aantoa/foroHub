package com.example.ForoHub.api.controller;

import com.example.ForoHub.domain.topico.DatosDetalleTopicoDTO;
import com.example.ForoHub.domain.curso.CursoRepository;
import com.example.ForoHub.domain.topico.*;
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
    public ResponseEntity<Page<DatosListaTopicoDTO>> listarTopicos(
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

    @GetMapping("/{id}")
    public ResponseEntity<DatosListaTopicoDTO> listarTopico(@PathVariable Long id) {
        var topico = topicoRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("El tópico con id " + id + " no existe"));
        return ResponseEntity.ok(new DatosListaTopicoDTO(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopicoDTO datos) {
        var optional = topicoRepository.findById(id);
        if (!optional.isPresent()) {
            throw new jakarta.persistence.EntityNotFoundException("El tópico con id " + id + " no existe");
        }
        var topico = optional.get();

        if (datos.titulo() != null && !datos.titulo().isBlank()
                && datos.mensaje() != null && !datos.mensaje().isBlank()
                && topicoRepository.existsByTituloAndMensajeAndIdNot(datos.titulo(), datos.mensaje(), id)) {
            throw new com.example.ForoHub.infra.exceptions.DuplicateResourceException(
                    "Ya existe otro tópico con el mismo título y mensaje."
            );
        }

        var nuevoCurso = (datos.cursoId() != null)
                ? cursoRepository.findById(datos.cursoId())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Curso no existe: " + datos.cursoId()))
                : null;

        topico.actualizarTopico(datos, nuevoCurso);
        topicoRepository.save(topico);

        return ResponseEntity.ok(new DatosDetalleTopicoDTO(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        var optional = topicoRepository.findById(id);

        if (!optional.isPresent()) {
            throw new EntityNotFoundException("El tópico con id " + id + " no existe");
        }

        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
