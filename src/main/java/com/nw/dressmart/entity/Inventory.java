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
public class Inventory {
    @Id
    @SequenceGenerator(
            name = "inventory_sequence",
            sequenceName = "inventory_sequence",
            initialValue = 20
    )
    @GeneratedValue(
            generator = "inventory_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id",referencedColumnName = "id",nullable = false)
    private Item item;

    private Integer quantity;

    private LocalDateTime createdOn;

    private LocalDateTime expiredOn;
}
