package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Expense extends BaseModel{
    private String description;
    @ManyToOne
    private User createdBy;
    private Long amount;
    @Enumerated(EnumType.ORDINAL)
    private ExpenseType expenseType;

    public Expense(User createdBy, Long amount, ExpenseType expenseType, String description) {
        this.description = description;
        this.createdBy = createdBy;
        this.amount = amount;
        this.expenseType = expenseType;
    }

    public Expense() {

    }
}
