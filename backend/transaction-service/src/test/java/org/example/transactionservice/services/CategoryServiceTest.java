package org.example.transactionservice.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.transactionservice.models.Category;
import org.example.transactionservice.models.Transaction;
import org.example.transactionservice.models.TransactionType;
import org.example.transactionservice.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1L)
                .title("Test Category")
                .transactions(Set.of(Transaction.builder()
                        .id(1L)
                        .title("Test Transaction")
                        .type(TransactionType.INCOME)
                        .value(100.0)
                        .time(LocalDateTime.now())
                        .build()))
                .build();
    }

    @Test
    void getAllCategories_ShouldReturnCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        List<Category> categories = categoryService.getAllCategories();
        assertFalse(categories.isEmpty());
        assertEquals(1, categories.size());
        assertEquals("Test Category", categories.get(0).getTitle());
    }

    @Test
    void getCategoryById_ShouldReturnCategory_WhenExists() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        Category foundCategory = categoryService.getCategoryById(1L);
        assertNotNull(foundCategory);
        assertEquals(1L, foundCategory.getId());
    }

    @Test
    void getCategoryById_ShouldThrowException_WhenNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> categoryService.getCategoryById(1L));
        assertEquals("Category not found", exception.getMessage());
    }

    @Test
    void deleteCategoryById_ShouldCallRepository() {
        doNothing().when(categoryRepository).deleteById(1L);
        categoryService.deleteCategoryById(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void saveCategory_ShouldReturnSavedCategory() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        Category savedCategory = categoryService.saveCategory(category);
        assertNotNull(savedCategory);
        assertEquals("Test Category", savedCategory.getTitle());
    }
}
