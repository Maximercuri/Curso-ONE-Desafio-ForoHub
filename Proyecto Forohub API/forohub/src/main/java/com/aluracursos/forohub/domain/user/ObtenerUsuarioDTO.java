package com.aluracursos.forohub.domain.user;

import com.aluracursos.forohub.domain.topico.ObtenerTopicoResumidoDTO;

import java.util.List;
import java.util.stream.Collectors;

public record ObtenerUsuarioDTO(Long id,
                                String nombreUsuario,
                                String email,
                                List<ObtenerTopicoResumidoDTO> topicos) {

    public ObtenerUsuarioDTO(Usuario usuario) {
        this(usuario.getId(),
                usuario.getNombreUsuario(),
                usuario.getEmail(),
                usuario.getTopicos().stream()
                        .map(ObtenerTopicoResumidoDTO::new)
                        .collect(Collectors.toList()));
    }

}
