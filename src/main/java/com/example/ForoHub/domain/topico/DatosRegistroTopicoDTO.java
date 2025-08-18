package com.example.ForoHub.domain.topico;

import com.example.ForoHub.domain.curso.Curso;
import com.example.ForoHub.domain.usuario.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record DatosRegistroTopicoDTO(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull Long autorId,
        @NotNull Long cursoId) {
}
