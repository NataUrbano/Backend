package com.library.backend_epilogo.repositories;

import com.library.backend_epilogo.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface LibroRepository extends JpaRepository<Book, Long> {
}




