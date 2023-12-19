package com.example.splitwise.Service;

import com.example.splitwise.Repository.ExpenseRepository;
import com.example.splitwise.Repository.ExpenseUserRepository;
import com.example.splitwise.Repository.GroupRepository;
import com.example.splitwise.Repository.UserRepository;
import com.example.splitwise.exceptions.GroupIdInvalidException;
import com.example.splitwise.exceptions.MemberIdInvalidException;
import com.example.splitwise.exceptions.UserIdInvalidException;
import com.example.splitwise.models.*;
import com.example.splitwise.strategy.SettleUpStrategy;
import com.example.splitwise.strategy.TwoHeapSettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SettleUpService {
    UserRepository userRepository;
    GroupRepository groupRepository;
    ExpenseUserRepository expenseUserRepository;
    SettleUpStrategy settleUpStrategy= new TwoHeapSettleUpStrategy();
    ExpenseRepository expenseRepository;
    @Autowired
    public SettleUpService(UserRepository userRepository, GroupRepository groupRepository, ExpenseUserRepository expenseUserRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.expenseUserRepository = expenseUserRepository;
        this.expenseRepository = expenseRepository;
    }

    public List<Transaction> settleUpGroup(Long userId, Long groupId) throws UserIdInvalidException, GroupIdInvalidException, MemberIdInvalidException {
        List<Transaction> transactions = new ArrayList<>();
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) throw new UserIdInvalidException();
        User user = optionalUser.get();
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if(optionalGroup.isEmpty()) throw new GroupIdInvalidException();
        Group group = optionalGroup.get();
        List<User> groupUsers = group.getUsers();
        boolean flag = false;
        for(User groupUser:groupUsers){
            if(groupUser.getId().equals(userId)){
                flag = true;
                break;
            }
        }
        if(!flag) throw new MemberIdInvalidException();
        List<Expense> expenses = group.getExpenses();
        if(expenses.isEmpty()) return transactions;
        List<ExpenseUser> expenseUsers = expenses.stream().map(expenseUserRepository::findAllByExpense).flatMap(Collection::stream).toList();
        transactions = settleUpStrategy.settleUp(expenseUsers);
        if(transactions.isEmpty()) return transactions;
        updateSettlement(transactions, user, group);
        return transactions;
    }
    public List<Transaction> settleUpUser(Long userId) throws UserIdInvalidException {
        List<Transaction> transactions = new ArrayList<>();
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) throw new UserIdInvalidException();
        User user = optionalUser.get();
        List<Expense> expenses = expenseRepository.findAll();
        if(expenses.isEmpty()) return transactions;
        List<ExpenseUser> expenseUsers = expenses.stream().map(expenseUserRepository::findAllByExpense).flatMap(Collection::stream).toList();
        transactions = settleUpStrategy.settleUp(expenseUsers);
        transactions = transactions.stream().filter((t)->t.getToUserId().equals(userId)||t.getFromUserId().equals(userId)).toList();
        if(transactions.isEmpty()) return transactions;
        updateSettlement(transactions, user, null);
        return transactions;
    }
    public void updateSettlement(List<Transaction> transactions, User user, Group group){
        Long transactionSum = transactions.stream().map(Transaction::getAmount).reduce(Long::sum).get();
        Expense dummyExpense = new Expense(user, transactionSum, ExpenseType.DUMMY, "Settlement");
        dummyExpense = expenseRepository.save(dummyExpense);
        if(group!=null)
        {
            group.getExpenses().add(dummyExpense);
            groupRepository.save(group);
        }
        for(Transaction transaction:transactions){
            User toUser = userRepository.findById(transaction.getToUserId()).get();
            User fromUser = userRepository.findById(transaction.getFromUserId()).get();
            Long amount = transaction.getAmount();
            expenseUserRepository.save(new ExpenseUser(fromUser, amount, dummyExpense, UserExpenseType.PAID));
            expenseUserRepository.save(new ExpenseUser(toUser, amount, dummyExpense, UserExpenseType.HASTOPAY));
        }
    }
}
