package com.example.eventos_api.domain.brand;
import com.example.eventos_api.domain.item.ItemSimpleDTO;
import java.util.List;
import java.util.UUID;

public record BrandResponseDTO(
        UUID id,
        String name,
        String description,
        List<ItemSimpleDTO> items
) {
    public BrandResponseDTO(Brand brand) {
        this(brand.getId(), brand.getName(), brand.getDescription(),
                brand.getItems().stream().map(ItemSimpleDTO::new).toList());
    }
}
