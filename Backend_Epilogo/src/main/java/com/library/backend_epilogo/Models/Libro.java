package com.library.backend_epilogo.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name= "libros")
@NoArgsConstructor
@AllArgsConstructor

public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Long idLibro;

    @Column(nullable = false, length = 13, unique = true)
    private String isbn;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 100)
    private String autor;

    @Column(name = "cantidad_total", nullable = false)
    private Integer cantidadTotal;

    @Column(name = "cantidad_disponible", nullable = false)
    private Integer cantidadDisponible;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_actual", nullable = false)
    private EstadoDisponibilidad estadoActual;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "libro_categoria",
            joinColumns = @JoinColumn(name = "id_libro"),
            inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    private Set<Categoria> categorias = new HashSet<>();

    public enum EstadoDisponibilidad {
        DISPONIBLE, POCAS_COPIAS, NO_DISPONIBLE
    }
}
