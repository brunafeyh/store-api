package com.example.eventos_api.repositories;

import com.example.eventos_api.domain.brand.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
}
