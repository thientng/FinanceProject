package com.example.financeproject.entity;

import java.util.Date;

public class Expenses {
    private int id;
    private String description;
    private float amount;
    private Date createat;
    private String category;

    public Expenses() {
    }

    public Expenses(int id, String description, float amount, Date createat, String category) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.createat = createat;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getCreateat() {
        return createat;
    }

    public void setCreateat(Date createat) {
        this.createat = createat;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
