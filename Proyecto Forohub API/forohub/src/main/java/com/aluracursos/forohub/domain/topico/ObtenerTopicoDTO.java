package com.aluracursos.forohub.domain.topico;

public record ObtenerTopicoDTO(Long id,
                               String nombreUsuario,
                               String titulo,
                               String mensaje ) {

    public ObtenerTopicoDTO(Topico topico){
        this(topico.getId(),
             topico.getUsuario().getNombreUsuario(),
             topico.getTitulo(),
             topico.getMensaje());
    }

}
