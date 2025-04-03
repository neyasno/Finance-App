package org.example.transactionservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.transactionservice.dto.SaveCategoryRequest;
import org.example.transactionservice.models.Category;
import org.example.transactionservice.services.CategoryService;
import org.example.transactionservice.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories;

        try {
            categories = categoryService.getAllCategories();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Valid SaveCategoryRequest request) {
        Category newCategory = Category.builder().title(request.getTitle()).build();
        return ResponseEntity.ok(categoryService.saveCategory(newCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid SaveCategoryRequest request) {
        Category category = categoryService.getCategoryById(id);
        category.setTitle(request.getTitle());
        return ResponseEntity.ok(categoryService.saveCategory(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }

}
