package com.aluracursos.forohub.domain.topico;

import com.aluracursos.forohub.domain.user.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Topico")
@Table(name = "topicos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public void actualizarDatos(ModificacionTopicoDTO topicoDTO) {

        if (topicoDTO.mensaje() != null && !topicoDTO.mensaje().isEmpty()){
            this.mensaje = topicoDTO.mensaje();
        }

        if (topicoDTO.titulo() != null && !topicoDTO.titulo().isEmpty()) {
            this.titulo = topicoDTO.titulo();
        }



    }

}
