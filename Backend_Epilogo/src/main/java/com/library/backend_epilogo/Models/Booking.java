package com.library.backend_epilogo.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_booking")
    private Long idBooking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;  // Relación Many-to-One con User

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_book", nullable = false)
    private Book book;  // Relación Many-to-One con Book

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "Expected_return_date", nullable = false)
    private LocalDate expectedReturnDate;


    @Column(name = "Actual_return_date")
    private LocalDate actualReturnDate;

    public enum BookingState {
        PENDIENTE,    // La reserva está esperando confirmación
        ACTIVA,       // La reserva está confirmada y en uso
        COMPLETADA,   // La reserva finalizó correctamente
        CANCELADA     // La reserva fue cancelada
    }
}
