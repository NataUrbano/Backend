package com.library.backend_epilogo.repositories;

import com.library.backend_epilogo.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ReservaRepository extends JpaRepository<Reservation, Long> {

}
