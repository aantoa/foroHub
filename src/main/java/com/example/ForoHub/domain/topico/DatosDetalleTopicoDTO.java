package com.example.ForoHub.domain.topico;

import java.time.LocalDateTime;

public record DatosDetalleTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        TopicoStatus status,
        String autor,
        String curso) {
    public DatosDetalleTopicoDTO(Topico t) {
        this(t.getId(), t.getTitulo(), t.getMensaje(), t.getFechaCreacion(), t.getStatus(), t.getAutor().getNombre(), t.getCurso().getNombre());
    }
}
