package com.example.eventos_api.domain.category;

import com.example.eventos_api.domain.item.ItemSimpleDTO;

import java.util.List;
import java.util.UUID;

public class CategoryResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private List<ItemSimpleDTO> items;

    public CategoryResponseDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.items = category.getItems().stream()
                .map(ItemSimpleDTO::new)
                .toList();
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<ItemSimpleDTO> getItems() {
        return items;
    }
    public void setItems(List<ItemSimpleDTO> items) {
        this.items = items;
    }
}
