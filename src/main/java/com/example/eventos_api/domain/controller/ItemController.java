package com.example.eventos_api.domain.controller;

import com.example.eventos_api.domain.item.Item;
import com.example.eventos_api.domain.item.ItemRequestDTO;
import com.example.eventos_api.domain.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item createItem(@RequestBody ItemRequestDTO dto) {
        return itemService.createItem(dto);
    }

    @GetMapping
    public List<Item> getAllItems(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID brandId,
            @RequestParam(required = false) String name
    ) {
        return itemService.getFilteredItems(categoryId, brandId, name);
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable UUID id) {
        return itemService.getItemById(id);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable UUID id, @RequestBody ItemRequestDTO dto) {
        return itemService.updateItem(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable UUID id) {
        itemService.deleteItem(id);
    }
}
