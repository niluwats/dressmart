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
@NamedStoredProcedureQuery(
        name = "create_order_product",
        procedureName = "usp_create_order_product",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN,name = "param_order_id",type = int.class),
                @StoredProcedureParameter(mode = ParameterMode.IN,name = "param_product_id",type = int.class),
                @StoredProcedureParameter(mode = ParameterMode.IN,name = "param_quantity",type = int.class),
        }
)
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;

    private Integer quantity;

    private BigDecimal subTotal;
}
