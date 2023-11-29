package com.nw.dressmart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @SequenceGenerator(
            name = "cart_item_sequence",
            sequenceName = "cart_item_sequence",
            initialValue = 20
    )
    @GeneratedValue(
            generator = "cart_item_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id",nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id",nullable = false,referencedColumnName = "id")
    private Item item;

    private Integer quantity;

    private BigDecimal subTotal;

    private LocalDateTime addedTimestamp;

    private Boolean status;

    @PostUpdate
    @PostPersist
    private void updateStock(){
        int updatedStock=item.getInventoryItem().getQuantity()-quantity;
        item.getInventoryItem().setQuantity(updatedStock);
    }
}
