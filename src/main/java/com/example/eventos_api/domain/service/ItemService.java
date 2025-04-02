package com.example.eventos_api.domain.service;

import com.example.eventos_api.domain.brand.Brand;
import com.example.eventos_api.domain.category.Category;
import com.example.eventos_api.domain.item.Item;
import com.example.eventos_api.domain.item.ItemRequestDTO;
import com.example.eventos_api.repositories.BrandRepository;
import com.example.eventos_api.repositories.CategoryRepository;
import com.example.eventos_api.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository, BrandRepository brandRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    public Item createItem(ItemRequestDTO dto) {
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        Brand brand = brandRepository.findById(dto.brandId())
                .orElseThrow(() -> new RuntimeException("Marca não encontrada"));

        Item item = new Item();
        item.setSku(dto.sku());
        item.setName(dto.name());
        item.setDescription(dto.description());
        item.setImageUrl(dto.imageUrl());
        item.setPrice(dto.price());
        item.setStock(dto.stock());
        item.setCategory(category);
        item.setBrand(brand);
        item.setCreatedAt(new Date());
        item.setUpdatedAt(new Date());

        return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(UUID id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }

    public Item updateItem(UUID id, ItemRequestDTO dto) {
        Item item = getItemById(id);

        item.setSku(dto.sku());
        item.setName(dto.name());
        item.setDescription(dto.description());
        item.setImageUrl(dto.imageUrl());
        item.setPrice(dto.price());
        item.setStock(dto.stock());
        item.setUpdatedAt(new Date());

        if (dto.categoryId() != null) {
            Category category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            item.setCategory(category);
        }

        if (dto.brandId() != null) {
            Brand brand = brandRepository.findById(dto.brandId())
                    .orElseThrow(() -> new RuntimeException("Marca não encontrada"));
            item.setBrand(brand);
        }

        return itemRepository.save(item);
    }

    public void deleteItem(UUID id) {
        itemRepository.deleteById(id);
    }
}
