package com.aluracursos.forohub.controllers;

import com.aluracursos.forohub.domain.user.CrearUsuarioDTO;
import com.aluracursos.forohub.domain.user.ObtenerUsuarioDTO;
import com.aluracursos.forohub.domain.user.Usuario;
import com.aluracursos.forohub.domain.user.ValidarUsuarioDTO;
import com.aluracursos.forohub.infra.security.jwt.JWTtokenDatos;
import com.aluracursos.forohub.infra.security.jwt.TokenService;
import com.aluracursos.forohub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ObtenerUsuarioDTO> registrarUsuario(@RequestBody @Valid CrearUsuarioDTO usuarioCrear) {
        var usuariocreado = service.crearUsuario(usuarioCrear);
        URI URL = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuariocreado.id())
                .toUri();
        return ResponseEntity.created(URL).body(usuariocreado);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTtokenDatos> autentificarUsuario(@RequestBody @Valid ValidarUsuarioDTO usuarioValidar){

        Authentication authToken = new UsernamePasswordAuthenticationToken(usuarioValidar.email(), usuarioValidar.clave());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        return ResponseEntity.ok().body(new JWTtokenDatos(JWTToken));

    }

}
