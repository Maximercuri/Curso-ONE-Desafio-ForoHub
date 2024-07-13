package com.aluracursos.forohub.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearUsuarioDTO(@NotBlank @Size(min = 4) String nombreUsuario,
                              @Email @NotBlank String email,
                              @NotBlank @Size(min = 6) String clave) {
}
