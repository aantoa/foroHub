package com.example.ForoHub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosListaTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        TopicoStatus topicoStatus,
        LocalDateTime fechaCreacion,
        Long autorId,
        Long cursoId)
{
    public DatosListaTopicoDTO (Topico topico){
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                topico.getFechaCreacion(),
                topico.getAutor().getId(),
                topico.getCurso().getId());
    }
}
