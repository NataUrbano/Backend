package com.library.backend_epilogo.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(nullable = false, length = 50, unique = true)
    private String nombre;

    @ManyToMany(mappedBy = "categorias", fetch = FetchType.LAZY)
    private Set<Libro> libros = new HashSet<>();
}
