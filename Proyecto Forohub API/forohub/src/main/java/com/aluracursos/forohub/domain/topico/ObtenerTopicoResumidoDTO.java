package com.aluracursos.forohub.domain.topico;

public record ObtenerTopicoResumidoDTO(Long id, String titulo) {

    public ObtenerTopicoResumidoDTO(Topico topico) {
        this(topico.getId(), topico.getTitulo());
    }

}
