package org.example.transactionservice.models;

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
    private Long id;

    private String title;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private Set<Transaction> transactions;
}
