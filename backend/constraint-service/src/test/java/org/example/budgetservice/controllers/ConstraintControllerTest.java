package org.example.budgetservice.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.budgetservice.exceptions.ConstraintNotFoundException;
import org.example.budgetservice.models.Constraint;
import org.example.budgetservice.services.ConstraintService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ConstraintControllerTest {

    @Mock
    private ConstraintService constraintService;

    private MockMvc mockMvc;

    private Constraint constraint;

    @BeforeEach
    void setUp() {
        ConstraintController constraintController = new ConstraintController(constraintService);
        mockMvc = MockMvcBuilders.standaloneSetup(constraintController).build();

        constraint = Constraint.builder()
                .id(1L)
                .value(500.0)
                .timeToExpire(LocalDateTime.now().plusDays(30))
                .userId(1L)
                .available(0.0)
                .build();
    }

    @Test
    void getConstraint_ShouldReturnConstraint() throws Exception {
        when(constraintService.getConstraint(1L, 1L)).thenReturn(constraint);

        mockMvc.perform(get("/1").header("X-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getConstraint_ShouldReturnNotFound_WhenConstraintNotFound() throws Exception {
        when(constraintService.getConstraint(1L, 1L)).thenThrow(new ConstraintNotFoundException("Constraint not found"));

        mockMvc.perform(get("/1").header("X-User-Id", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createConstraint_ShouldReturnCreatedConstraint() throws Exception {
        when(constraintService.createConstraint(any(Constraint.class))).thenReturn(constraint);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-User-Id", 1L)
                        .content("{\"value\": 500.0, \"expirationTime\": \"2025-12-31T23:59:59\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(500.0));
    }

    @Test
    void updateConstraint_ShouldReturnUpdatedConstraint() throws Exception {
        when(constraintService.getConstraint(1L, 1L)).thenReturn(constraint);
        when(constraintService.updateConstraint(any(Constraint.class))).thenReturn(constraint);

        mockMvc.perform(put("/1")
                        .header("X-User-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\": 600.0, \"time\": \"2025-12-31T23:59:59\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deleteConstraint_ShouldReturnOk_WhenDeleted() throws Exception {
        doNothing().when(constraintService).deleteConstraint(1L, 1L);

        mockMvc.perform(delete("/1").header("X-User-Id", "1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllConstraints_ShouldReturnConstraints() throws Exception {
        List<Constraint> constraints = List.of(constraint);
        when(constraintService.getAllConstraints(1L)).thenReturn(constraints);

        mockMvc.perform(get("/").header("X-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
