package com.nw.dressmart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItem {
    @Id
    @SequenceGenerator(
            name = "order_item_sequence",
            sequenceName = "order_item_sequence",
            initialValue = 20
    )
    @GeneratedValue(
            generator = "order_item_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id",referencedColumnName = "id")
    private Item item;

    private Integer quantity;

    private BigDecimal subTotal;
}
