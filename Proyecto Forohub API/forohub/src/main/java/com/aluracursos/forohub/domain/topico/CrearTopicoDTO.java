package com.aluracursos.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record CrearTopicoDTO(@NotBlank String nombreUsuario,
                             @NotBlank String titulo,
                             String mensaje ) {
}
