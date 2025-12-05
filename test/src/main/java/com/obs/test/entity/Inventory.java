package com.obs.test.entity;

import com.obs.test.utils.TypeTransaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_seq_gen")
    @SequenceGenerator(name = "inventory_seq_gen", sequenceName = "inventory_seq", allocationSize = 1)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @NotNull
    private Item item;
    @NotNull
    @Min(value =  1, message = "Quantity must be at least 1")
    private Integer qty;
    @Enumerated(EnumType.STRING)
    @NotNull
    private TypeTransaction typeTransaction;
}
