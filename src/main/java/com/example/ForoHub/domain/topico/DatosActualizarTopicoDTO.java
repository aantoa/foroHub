package com.example.ForoHub.domain.topico;

public record DatosActualizarTopicoDTO(
        String titulo,
        String mensaje,
        TopicoStatus status,
        Long cursoId
) {
}
