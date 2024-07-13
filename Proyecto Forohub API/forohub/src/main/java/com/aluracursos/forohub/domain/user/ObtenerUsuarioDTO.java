package com.aluracursos.forohub.domain.user;

import com.aluracursos.forohub.domain.topico.ObtenerTopicoDTO;
import com.aluracursos.forohub.domain.topico.ObtenerTopicoResumidoDTO;
import com.aluracursos.forohub.domain.topico.Topico;

import java.util.List;

public record ObtenerUsuarioDTO(Long id,
                                String nombreUsuario,
                                String email,
                                List<ObtenerTopicoResumidoDTO> topicosResumidos) {
}
