package com.ddf.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "order_item")
public class OrderItemDomain {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @NotNull
    private OrderDomain order;

    @ManyToOne
    @NotNull
    private ProductDomain product;

    @NotNull
    private int quantity;

    @NotNull
    private Double value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderDomain getOrder() {
        return order;
    }

    public void setOrder(OrderDomain order) {
        this.order = order;
    }

    public ProductDomain getProduct() {
        return product;
    }

    public void setProduct(ProductDomain product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
