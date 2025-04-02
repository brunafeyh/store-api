package com.example.eventos_api.domain.item;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemRequestDTO(
        String sku,
        String name,
        String description,
        String imageUrl,
        BigDecimal price,
        int stock,
        UUID categoryId,
        UUID brandId
) {}
