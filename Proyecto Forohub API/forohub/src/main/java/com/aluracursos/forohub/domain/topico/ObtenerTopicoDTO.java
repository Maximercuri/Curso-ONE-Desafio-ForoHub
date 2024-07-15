package com.aluracursos.forohub.domain.topico;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record ObtenerTopicoDTO(Long id,
                               String nombreUsuario,
                               @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime fechaCreacion,
                               String titulo,
                               String mensaje ) {

    public ObtenerTopicoDTO(Topico topico){
        this(topico.getId(),
             topico.getUsuario().getNombreUsuario(),
             topico.getFechaCreacion(),
             topico.getTitulo(),
             topico.getMensaje());
    }

}
