package com.aluracursos.forohub.domain.topico.validations;

import com.aluracursos.forohub.domain.topico.CrearTopicoDTO;
import com.aluracursos.forohub.domain.user.UsuarioRepository;
import com.aluracursos.forohub.infra.errors.ExcepcionDeValidacionEnCreacion;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidarUsuarioExistente implements ValidadorDeTopicos{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(CrearTopicoDTO topico) {

        var usuarioExistente = usuarioRepository.existsByNombreUsuario(topico.nombreUsuario());

        if (!usuarioExistente) {
            throw new ExcepcionDeValidacionEnCreacion("No Existe Ningún Usuario con ese Nombre");
        }

    }
}
