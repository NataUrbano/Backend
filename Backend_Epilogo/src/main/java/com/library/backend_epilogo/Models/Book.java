package com.library.backend_epilogo.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book")
    private Long idBook;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor", nullable = false)
    private Authors autor;  // Relación Many-to-One con Authors

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Column(name = "enable_amount", nullable = false)
    private Integer enableAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "actual_state", nullable = false)
    private StateBook actualState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;  // Relación Many-to-One con Category

    @Column(name = "register_date")
    @CreationTimestamp
    private LocalDateTime registerDate;

    public enum StateBook {
        DISPONIBLE, POCAS_COPIAS, NO_DISPONIBLE
    }
}