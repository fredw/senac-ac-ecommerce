package com.ddf.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
public class Role {

    public static final Long ADMIN = 1L;
    public static final Long CUSTOMER = 2L;

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
