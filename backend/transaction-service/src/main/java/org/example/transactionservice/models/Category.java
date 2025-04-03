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

    private String title;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private Set<Transaction> transactions;

    @Transient
    private Double dayIncome;

    @Transient
    private Double dayOutcome;

    @Transient
    private Double monthIncome;

    @Transient
    private Double monthOutcome;

//    @Transient
//    private Double yearIncome;
//
//    @Transient
//    private Double yearOutcome;
}
