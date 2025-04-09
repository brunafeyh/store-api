package com.example.eventos_api.repositories;

import com.example.eventos_api.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findByCategoryId(UUID categoryId);
    List<Item> findByBrandId(UUID brandId);
    List<Item> findByCategoryIdAndBrandId(UUID categoryId, UUID brandId);
}

