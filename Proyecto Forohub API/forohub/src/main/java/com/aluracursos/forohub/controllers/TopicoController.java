package com.aluracursos.forohub.controllers;

import com.aluracursos.forohub.domain.topico.CrearTopicoDTO;
import com.aluracursos.forohub.domain.topico.ModificacionTopicoDTO;
import com.aluracursos.forohub.domain.topico.ObtenerTopicoDTO;
import com.aluracursos.forohub.domain.topico.ObtenerTopicoResumidoDTO;
import com.aluracursos.forohub.service.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/topico")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping()
    public ResponseEntity<ObtenerTopicoDTO> crearTopico(@RequestBody @Valid CrearTopicoDTO topicoACrear) {

        ObtenerTopicoDTO topicoCreado = topicoService.crearTopico(topicoACrear);
        URI uri = URI.create("/topico/" + topicoCreado.id());
        return ResponseEntity.created(uri).body(topicoCreado);

    }

    @GetMapping()
    public ResponseEntity<Page<ObtenerTopicoResumidoDTO>> obtenerTopicos(@PageableDefault(size = 5) Pageable pageable) {

        Page<ObtenerTopicoResumidoDTO> topicos = topicoService.obtenerTodosLosTopicos(pageable);

        return ResponseEntity.ok(topicos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ObtenerTopicoDTO> obtenerTopico(@PathVariable Long id) {

        ObtenerTopicoDTO topico = topicoService.obtenerTopico(id);

        return ResponseEntity.ok(topico);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity borrarTopico(@PathVariable Long id) {

        topicoService.borrarTopico(id);

        return ResponseEntity.accepted().build();

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ObtenerTopicoDTO> modificarTopico(@RequestBody @Valid ModificacionTopicoDTO topicoModificado, @PathVariable Long id) {

        ObtenerTopicoDTO topicoFinal = topicoService.modificarTopico(topicoModificado, id);

        return ResponseEntity.ok(topicoFinal);

    }

}