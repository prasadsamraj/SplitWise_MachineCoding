package com.example.splitwise.Repository;

import com.example.splitwise.models.Expense;
import com.example.splitwise.models.ExpenseUser;
import com.example.splitwise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseUserRepository extends JpaRepository<ExpenseUser, Long> {
    List<ExpenseUser> findAllByUser(User user);
    List<ExpenseUser> findAllByExpense(Expense expense);
}
