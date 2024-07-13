package com.aluracursos.forohub.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearUsuarioDTO(@NotBlank(message = "El Nombre De Usuario Es Obligatorio")
                              @Size(min = 4, message = "El nombre de Usuario Debe de Tener Mínimo 4 Caracteres")
                              String nombreUsuario,

                              @Email(message = "Formato de Email Invalido")
                              @NotBlank(message = "El email Es Obligatorio")
                              String email,

                              @NotBlank(message = "La Contraseña Es Obligatoria")
                              @Size(min = 6, message = "La Contraseña debe de Tener Mínimo 6 Caracteres")
                              String clave ) {
}
