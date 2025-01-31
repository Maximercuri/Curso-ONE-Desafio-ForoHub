package com.aluracursos.forohub.service;

import com.aluracursos.forohub.domain.topico.ObtenerTopicoDTO;
import com.aluracursos.forohub.domain.topico.ObtenerTopicoResumidoDTO;
import com.aluracursos.forohub.domain.user.*;
import com.aluracursos.forohub.infra.errors.ExcepcionDeValidacionEnCreacion;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ObtenerUsuarioDTO crearUsuario(CrearUsuarioDTO usuarioDTO) {

        var usuarioExistente = repository.existsByEmail(usuarioDTO.email());

        if (usuarioExistente) {
            throw new ExcepcionDeValidacionEnCreacion("Este Email Ya tiene una Cuenta Registrada");
        }

        var claveCodificada = passwordEncoder.encode(usuarioDTO.clave());

        Usuario usuario = Usuario.builder()
                .nombreDeUsuario(usuarioDTO.nombreUsuario())
                .email(usuarioDTO.email())
                .clave(claveCodificada)
                .topicos(new ArrayList<>())
                .build();

        repository.save(usuario);

        return new ObtenerUsuarioDTO(usuario);
    }


    public Page<ObtenerUsuariosCreados> obtenerTodosLosUsuario(Pageable pageable) {
        return repository.findAll(pageable)
                .map(ObtenerUsuariosCreados::new);
    }

    public ObtenerUsuarioDTO obtenerUsuario(Long id) {

        var usuario = repository.findById(id);

        if ((usuario.isEmpty())) {
            throw new ValidationException("No se ha Encontrado Ningún Usuario con ese id. Por Favor Ingrese Otro");
        }

        return new ObtenerUsuarioDTO(usuario.get());
    }

}
