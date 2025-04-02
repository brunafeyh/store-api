package com.example.eventos_api.repositories;

import com.example.eventos_api.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
