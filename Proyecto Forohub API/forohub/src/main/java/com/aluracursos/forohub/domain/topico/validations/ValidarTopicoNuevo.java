package com.aluracursos.forohub.domain.topico.validations;

import com.aluracursos.forohub.domain.topico.CrearTopicoDTO;
import com.aluracursos.forohub.domain.topico.TopicoRepository;
import com.aluracursos.forohub.domain.user.UsuarioRepository;
import com.aluracursos.forohub.infra.errors.ExcepcionDeValidacionEnCreacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarTopicoNuevo implements ValidadorDeTopicos{

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(CrearTopicoDTO topico) {

        var usuario = usuarioRepository.findByNombreUsuario(topico.nombreUsuario()).orElseThrow(() -> new ExcepcionDeValidacionEnCreacion("No se ha Encontrado El Usuario Indicado"));
        var topicoIdenticoConMismoUsuario = topicoRepository.existsByTituloAndMensajeAndUsuario(topico.titulo(), topico.mensaje(), usuario);

        if (topicoIdenticoConMismoUsuario) {
            throw new ExcepcionDeValidacionEnCreacion("Ya Existe un Tópico Idéntico de Este Usuario");
        }

    }

}
