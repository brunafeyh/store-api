package com.example.eventos_api.domain.controller;
import com.example.eventos_api.domain.category.Category;
import com.example.eventos_api.domain.category.CategoryDTO;
import com.example.eventos_api.domain.category.CategoryResponseDTO;
import com.example.eventos_api.domain.service.CategoryService;
import com.example.eventos_api.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.name());
        category.setDescription(dto.description());
        return categoryService.createCategory(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(
            @RequestParam(required = false) String name
    ) {
        List<Category> categories = categoryRepository.findAll();

        if (name != null && !name.isBlank()) {
            String lowerName = name.toLowerCase();
            categories = categories.stream()
                    .filter(cat -> cat.getName() != null && cat.getName().toLowerCase().contains(lowerName))
                    .toList();
        }

        List<CategoryResponseDTO> response = categories.stream()
                .map(CategoryResponseDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable UUID id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable UUID id, @RequestBody CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.name());
        category.setDescription(dto.description());
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }
}
