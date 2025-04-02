package com.example.eventos_api.repositories;

import com.example.eventos_api.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
}
