package com.nw.dressmart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InventoryItem {
    @Id
    @SequenceGenerator(
            name = "inventory_item_sequence",
            sequenceName = "inventory_item_sequence",
            initialValue = 20
    )
    @GeneratedValue(
            generator = "inventory_item_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @OneToOne
    @JoinColumn(name = "item_id",referencedColumnName = "id")
    private Item item;

    private Integer quantity;

    private LocalDateTime updatedOn;
}
