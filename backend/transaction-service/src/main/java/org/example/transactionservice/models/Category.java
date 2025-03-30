package org.example.transactionservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category{
    @Id
    private Long id;

    private String title;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private Set<Transaction> transactions;
}
