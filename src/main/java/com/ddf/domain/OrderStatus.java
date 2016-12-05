package com.ddf.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class OrderStatus {

    public static Long PAYMENT_WAITING = 1L;
    public static Long PAYMENT_COMPLETED = 2L;
    public static Long CANCELED = 3L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "The name must not be null")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
