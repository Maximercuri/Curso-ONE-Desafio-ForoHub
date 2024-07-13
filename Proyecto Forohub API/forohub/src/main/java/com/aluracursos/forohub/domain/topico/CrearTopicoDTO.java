package com.aluracursos.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record CrearTopicoDTO(@NotBlank(message = "Para Crear Un Tópico Debe Ser Bajo Un Nombre De Usuario Existente")
                             String nombreUsuario,
                             @NotBlank(message = "El Título No Puede Estar Vacío")
                             String titulo,
                             @NotBlank(message = "El Cuerpo Del Mensaje No puede Estar Vacío")
                             String mensaje ) {
}
