package com.example.eventos_api.domain.controller;
import com.example.eventos_api.domain.category.Category;
import com.example.eventos_api.domain.category.CategoryDTO;
import com.example.eventos_api.domain.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
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
    public List<CategoryDTO> listCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return categories.stream()
                .map(cat -> new CategoryDTO(cat.getName(), cat.getDescription()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable UUID id) {
        Category category = categoryService.getCategoryById(id);
        return new CategoryDTO(category.getName(), category.getDescription());
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
