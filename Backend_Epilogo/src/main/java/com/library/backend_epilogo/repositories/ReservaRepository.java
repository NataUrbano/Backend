package com.library.backend_epilogo.repositories;

import com.library.backend_epilogo.Models.Reserva;
import com.library.backend_epilogo.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuarioIdUsuario(Long idUsuario);
    List<Reserva> findByLibroIdLibro(Long idLibro);
    List<Reserva> findByEstado(Reserva.EstadoReserva estado);
    List<Reserva> findByFechaDevolucionEsperadaBefore(LocalDate fecha);
    
    List<Reserva> findByLibroIdLibroAndEstado(Long idLibro, Reserva.EstadoReserva estado);
}
