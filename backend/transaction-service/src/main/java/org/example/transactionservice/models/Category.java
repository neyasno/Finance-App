package org.example.transactionservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private String title;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transaction> transactions;

    @Transient
    private Double dayIncome;

    @Transient
    private Double dayOutcome;

    @Transient
    private Double monthIncome;

    @Transient
    private Double monthOutcome;

    @Transient
    private Double yearIncome;

    @Transient
    private Double yearOutcome;
}
