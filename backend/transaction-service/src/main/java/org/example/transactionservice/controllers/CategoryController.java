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

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestHeader("X-User-Id") Long userId
    ) {
        List<Category> categories;

        try {
            categories = categoryService.getAllCategories(userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) {
        try {
            return ResponseEntity.ok(categoryService.getCategoryById(id, userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(
            @RequestBody @Valid SaveCategoryRequest request,
            @RequestHeader("X-User-Id") Long userId
    ) {
        try {
            Category newCategory = Category.builder().title(request.getTitle()).userId(userId).build();
            return ResponseEntity.ok(categoryService.saveCategory(newCategory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid SaveCategoryRequest request,
            @RequestHeader("X-User-Id") Long userId
    ) {
        try {
            Category category = categoryService.getCategoryById(id, userId);
            category.setTitle(request.getTitle());
            return ResponseEntity.ok(categoryService.saveCategory(category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) {
        try {
            categoryService.deleteCategoryById(id, userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
