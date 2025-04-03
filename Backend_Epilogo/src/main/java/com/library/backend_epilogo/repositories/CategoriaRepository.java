package com.library.backend_epilogo.repositories;

import com.library.backend_epilogo.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CategoriaRepository extends JpaRepository<Category, Long> {

}
