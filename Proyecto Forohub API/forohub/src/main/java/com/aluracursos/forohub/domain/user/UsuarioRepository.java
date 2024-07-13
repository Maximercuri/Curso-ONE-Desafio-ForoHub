package com.aluracursos.forohub.domain.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository {

    Optional<Usuario> findByEmail(String email);

}
