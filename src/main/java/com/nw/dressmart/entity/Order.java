package com.nw.dressmart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@NamedStoredProcedureQuery(
        name = "create_order",
        procedureName = "usp_create_order",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN,name = "param_user_id",type = int.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT,name = "order_id",type = int.class)
        }
)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id",nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "order")
    private List<OrderProduct> orderProducts;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    private LocalDateTime orderTimestamp;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
