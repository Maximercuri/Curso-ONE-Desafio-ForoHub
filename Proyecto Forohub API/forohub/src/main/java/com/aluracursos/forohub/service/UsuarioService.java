package com.aluracursos.forohub.service;

import com.aluracursos.forohub.domain.topico.ObtenerTopicoDTO;
import com.aluracursos.forohub.domain.topico.ObtenerTopicoResumidoDTO;
import com.aluracursos.forohub.domain.user.CrearUsuarioDTO;
import com.aluracursos.forohub.domain.user.ObtenerUsuarioDTO;
import com.aluracursos.forohub.domain.user.Usuario;
import com.aluracursos.forohub.domain.user.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public ObtenerUsuarioDTO crearUsuario(CrearUsuarioDTO usuarioDTO) {

        var usuarioExistente = repository.existsByEmail(usuarioDTO.email());

        if (usuarioExistente) {
            throw new ValidationException("Este Email Ya tiene una Cuenta Registrada");
        }

        var claveCodificada = passwordEncoder.encode(usuarioDTO.clave());

        Usuario usuario = Usuario.builder()
                .nombreDeUsuario(usuarioDTO.nombreUsuario())
                .email(usuarioDTO.email())
                .clave(claveCodificada)
                .topicos(new ArrayList<>())
                .build();

        repository.save(usuario);

        var topicos = usuario.getTopicos().stream()
                .map(ObtenerTopicoResumidoDTO::new)
                .collect(Collectors.toList());

        return new ObtenerUsuarioDTO(usuario.getId(), usuario.getNombreUsuario(), usuario.getEmail(), topicos);
    }


}
