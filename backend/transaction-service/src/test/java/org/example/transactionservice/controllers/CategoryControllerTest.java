package org.example.transactionservice.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.transactionservice.dto.SaveCategoryRequest;
import org.example.transactionservice.models.Category;
import org.example.transactionservice.models.Transaction;
import org.example.transactionservice.models.TransactionType;
import org.example.transactionservice.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Category category;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
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
    void getAllCategories_ShouldReturnCategories() throws Exception {
        when(categoryService.getAllCategories(1L)).thenReturn(List.of(category));
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Category"));
    }

    @Test
    void getCategoryById_ShouldReturnCategory_WhenExists() throws Exception {
        when(categoryService.getCategoryById(1L, 1L)).thenReturn(category);
        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Category"));
    }

    @Test
    void createCategory_ShouldReturnCreatedCategory() throws Exception {
        when(categoryService.saveCategory(any(Category.class))).thenReturn(category);
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Category"));
    }

    @Test
    void updateCategory_ShouldReturnUpdatedCategory() throws Exception {
        SaveCategoryRequest request = new SaveCategoryRequest("Updated Category");

        String categoryJson = objectMapper.writeValueAsString(request);

        when(categoryService.getCategoryById(1L, 1L)).thenReturn(category);
        when(categoryService.saveCategory(any(Category.class))).thenReturn(category);
        mockMvc.perform(put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Category"));
    }

    @Test
    void deleteCategoryById_ShouldReturnNoContent() throws Exception {
        doNothing().when(categoryService).deleteCategoryById(1L, 1L);
        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isNoContent());
    }
}