package com.nw.dressmart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @SequenceGenerator(
            name = "cart_sequence",
            sequenceName = "cart_sequence",
            initialValue = 20
    )
    @GeneratedValue(
            generator = "cart_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<CartItem> cartItems;

    @Formula("(SELECT COALESCE(SUM(ci.sub_total),0) FROM cart_item ci WHERE ci.cart_id=id)")
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private LocalDateTime updatedTimestamp;
}
