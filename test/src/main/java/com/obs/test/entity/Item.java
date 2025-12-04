package com.obs.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq_gen")
    @SequenceGenerator(name = "item_seq_gen", sequenceName = "item_seq", allocationSize = 1)
    private Integer id;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Price cannot be null")
    private Integer price;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Inventory> inventories;
}
