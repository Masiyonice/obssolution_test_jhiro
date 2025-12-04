package com.obs.test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Order {
    @Id
    private String orderNo;
    @ManyToOne
    @JoinColumn(name = "item_id")
    @NotNull
    private Item item;
    @NotNull
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    @NotNull
    private Integer price;
}
