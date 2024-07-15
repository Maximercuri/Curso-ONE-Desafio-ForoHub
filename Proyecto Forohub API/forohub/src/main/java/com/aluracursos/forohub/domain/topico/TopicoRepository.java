package com.aluracursos.forohub.domain.topico;

import com.aluracursos.forohub.domain.user.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensajeAndUsuario(String tituloTopico, String mensaje, Usuario usuarioDelTopico);

    Page<Topico> findAll(Pageable pageable);

}
