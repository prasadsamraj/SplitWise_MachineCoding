package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity(name = "splitwisegroup")
public class Group extends BaseModel{
    private String name;
    @ManyToOne
    private User admin;
    @ManyToMany
    private List<User> users;
    @OneToMany
    private List<Expense> expenses;
}
