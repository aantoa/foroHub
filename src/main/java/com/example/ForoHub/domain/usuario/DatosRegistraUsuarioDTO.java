package com.example.ForoHub.domain.usuario;

import com.example.ForoHub.domain.perfil.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record DatosRegistraUsuarioDTO(
        Long id,
        @NotBlank String nombre,
        @NotBlank String contrasena,
        @NotBlank @Email String email,
        @NotBlank Set<Perfil> perfiles
) {
}
