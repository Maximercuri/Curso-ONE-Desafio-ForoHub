package com.aluracursos.forohub.domain.user;

public record ObtenerUsuariosCreados(Long id, String nombreUsuario) {

    public ObtenerUsuariosCreados(Usuario usuario) {
        this(usuario.getId(), usuario.getNombreUsuario());
    }

}
