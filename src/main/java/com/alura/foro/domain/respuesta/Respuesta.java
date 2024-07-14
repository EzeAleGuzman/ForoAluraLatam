package com.alura.foro.domain.respuesta;

import com.alura.foro.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Table
@Entity(name = "respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    private boolean solucion;

    @ManyToOne
    @JoinColumn(name = "id_topico")
    private Topico topico;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;


}
