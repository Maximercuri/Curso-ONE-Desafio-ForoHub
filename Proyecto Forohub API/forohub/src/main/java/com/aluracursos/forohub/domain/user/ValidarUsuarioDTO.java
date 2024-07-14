package com.aluracursos.forohub.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ValidarUsuarioDTO(@NotBlank(message = "Para Iniciar Sesión Se Debe Brindar Un Email")
                                @Email(message = "Debe Tener Un Formato de Email Valido")
                                String email,
                                @NotBlank(message = "Para Iniciar Sesión Se Debe Brindar una Contraseña")
                                String clave) {
}
