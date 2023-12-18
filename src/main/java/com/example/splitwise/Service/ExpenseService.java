package com.example.splitwise.Service;

import com.example.splitwise.Repository.ExpenseRepository;
import com.example.splitwise.Repository.ExpenseUserRepository;
import com.example.splitwise.Repository.GroupRepository;
import com.example.splitwise.Repository.UserRepository;
import com.example.splitwise.commands.PayType;
import com.example.splitwise.commands.SplitType;
import com.example.splitwise.exceptions.GroupIdInvalidException;
import com.example.splitwise.exceptions.MemberIdInvalidException;
import com.example.splitwise.exceptions.UserIdInvalidException;
import com.example.splitwise.models.*;
import com.example.splitwise.strategy.SplitAmountStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    ExpenseUserRepository expenseUserRepository;
    UserRepository userRepository;
    ExpenseRepository expenseRepository;
    GroupRepository groupRepository;
    @Autowired
    public ExpenseService(ExpenseUserRepository expenseUserRepository, UserRepository userRepository, ExpenseRepository expenseRepository, GroupRepository groupRepository) {
        this.expenseUserRepository = expenseUserRepository;
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.groupRepository = groupRepository;
    }

    public void addExpenseUsers(Long createdByUserId, List<Long> userIds, Long totalAmount, String desc, PayType payType, List<Long> paidAmounts, SplitType splitType, List<Long> splitAmounts) throws UserIdInvalidException {
        User createdUser = fetchUserById(createdByUserId);
        List<User> paidUsers = new ArrayList<>();
        for(Long userId:userIds) {
            paidUsers.add(fetchUserById(userId));
        }
        Expense expense = new Expense(createdUser, totalAmount, ExpenseType.REAL, desc);
        expenseRepository.save(expense);
        if(payType.equals(PayType.iPay)){
            expenseUserRepository.save(new ExpenseUser(createdUser, totalAmount, expense, UserExpenseType.PAID));
        }else{
            for(int i=0; i<userIds.size(); i++){
                expenseUserRepository.save(new ExpenseUser(paidUsers.get(i), paidAmounts.get(i), expense, UserExpenseType.PAID));
            }
        }
        splitAmounts = SplitAmountStrategyFactory.getSplitAmountStrategyFactory(splitType).splitAmount(userIds.size(), splitAmounts, totalAmount);
        for(int i=0; i<userIds.size(); i++){
            expenseUserRepository.save(new ExpenseUser(paidUsers.get(i), splitAmounts.get(i), expense, UserExpenseType.HASTOPAY));
        }
    }
    public void addExpenseGroup(Long createdByUserId, Long groupId, Long totalAmount, String desc, PayType payType, List<Long> paidAmounts, SplitType splitType, List<Long> splitAmounts) throws UserIdInvalidException, GroupIdInvalidException, MemberIdInvalidException {
        User createdUser = fetchUserById(createdByUserId);
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if(optionalGroup.isEmpty()) throw new GroupIdInvalidException();
        Group group = optionalGroup.get();
        List<User> hasToPayUsers = group.getUsers();
        boolean flag = false;
        for(User user:hasToPayUsers){
            if(user.getId().equals(createdByUserId)){
                flag = true;
                break;
            }
        }
        if(!flag) throw new MemberIdInvalidException();
        Expense expense = new Expense(createdUser, totalAmount, ExpenseType.REAL, desc);
        expenseRepository.save(expense);
        group.getExpenses().add(expense);
        groupRepository.save(group);
        expenseUserRepository.save(new ExpenseUser(createdUser, totalAmount, expense, UserExpenseType.PAID));
        splitAmounts = SplitAmountStrategyFactory.getSplitAmountStrategyFactory(splitType).splitAmount(hasToPayUsers.size(), splitAmounts, totalAmount);
        for(int i=0; i<hasToPayUsers.size(); i++){
            expenseUserRepository.save(new ExpenseUser(hasToPayUsers.get(i), splitAmounts.get(i), expense, UserExpenseType.HASTOPAY));
        }
    }
    private User fetchUserById(Long createdByUserId) throws UserIdInvalidException {
        Optional<User> optionalUser = userRepository.findById(createdByUserId);
        if(optionalUser.isEmpty()) throw new UserIdInvalidException();
        return optionalUser.get();
    }
    public Long myTotal(Long userId) throws UserIdInvalidException {
        User user = fetchUserById(userId);
        List<ExpenseUser> expenseUsers = expenseUserRepository.findAllByUser(user);
        Long myTotal = 0L;
        for(ExpenseUser expenseUser:expenseUsers){
            if(expenseUser.getUserExpenseType().equals(UserExpenseType.PAID))
                myTotal+=expenseUser.getAmount();
            else
                myTotal-=expenseUser.getAmount();
        }
        return myTotal;
    }
}
