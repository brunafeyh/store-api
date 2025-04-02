package com.example.eventos_api.domain.item;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ItemRequestDTO(
        String sku,
        String name,
        String description,
        List<String> imageUrls,
        BigDecimal price,
        int stock,
        UUID categoryId,
        UUID brandId
) {}
