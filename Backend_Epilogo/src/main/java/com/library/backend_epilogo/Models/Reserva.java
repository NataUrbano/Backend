package com.library.backend_epilogo.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_libro")
    private Libro libro;

    @Column(name = "fecha_reserva")
    private LocalDate fechaReserva;

    @Column(name = "fecha_devolucion_esperada", nullable = false)
    private LocalDate fechaDevolucionEsperada;

    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;

    @Column(name = "fecha_devolucion_real")
    private LocalDate fechaDevolucionReal;

    public enum EstadoReserva {
        PENDIENTE, ACTIVA, COMPLETADA, CANCELADA
    }
}
