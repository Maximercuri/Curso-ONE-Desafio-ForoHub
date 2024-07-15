package com.aluracursos.forohub.service;

import com.aluracursos.forohub.domain.topico.*;
import com.aluracursos.forohub.domain.topico.validations.ValidadorDeTopicos;
import com.aluracursos.forohub.domain.user.UsuarioRepository;
import com.aluracursos.forohub.infra.errors.ExcepcionDeValidacionEnCreacion;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    List<ValidadorDeTopicos> validadores;

    public ObtenerTopicoDTO crearTopico(CrearTopicoDTO topicoDTO) {

        validadores.forEach(validador -> validador.validar(topicoDTO));

        var usuario = usuarioRepository.findByNombreUsuario(topicoDTO.nombreUsuario()).orElseThrow(() -> new ExcepcionDeValidacionEnCreacion("No sé Encontró un Usuario con ese Nombre"));

        Topico topicoCreado = Topico.builder()
                .usuario(usuario)
                .titulo(topicoDTO.titulo())
                .fechaCreacion(LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")))
                .mensaje(topicoDTO.mensaje())
                .build();

        repository.save(topicoCreado);

        return new ObtenerTopicoDTO(topicoCreado);

    }

    public Page<ObtenerTopicoResumidoDTO> obtenerTodosLosTopicos(Pageable pageable) {
        return repository.findAll(pageable)
                .map(ObtenerTopicoResumidoDTO::new);
    }

    public ObtenerTopicoDTO obtenerTopico(Long id) {

        var topico = repository.findById(id);

        if (topico.isEmpty()){
            throw new ValidationException("No Se ha Encontrado Ningún Topico con ese Id. Por Favor Ingrese Otro");
        }

        return new ObtenerTopicoDTO(topico.get());

    }
}
