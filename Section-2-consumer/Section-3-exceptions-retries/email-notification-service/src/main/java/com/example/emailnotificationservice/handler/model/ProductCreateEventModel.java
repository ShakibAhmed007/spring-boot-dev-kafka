package com.example.emailnotificationservice.handler.model;

import java.math.BigDecimal;

public class ProductCreateEventModel {

    private Long id;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public ProductCreateEventModel(){}

    public ProductCreateEventModel(Long id, String title, BigDecimal price, Integer quantity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
