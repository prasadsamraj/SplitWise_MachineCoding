package com.example.splitwise.strategy;

import com.example.splitwise.Repository.UserRepository;
import com.example.splitwise.Service.Transaction;
import com.example.splitwise.models.ExpenseUser;
import com.example.splitwise.models.UserExpenseType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class TwoHeapSettleUpStrategy implements SettleUpStrategy{
    @Autowired
    UserRepository userRepository;
    @Override
    public List<Transaction> settleUp(List<ExpenseUser> expenseUsers) {
        HashMap<Long, Long> hashMap = new HashMap<>();
        for(ExpenseUser expenseUser:expenseUsers){
            Long userId = expenseUser.getUser().getId();
            if(!hashMap.containsKey(userId)) hashMap.put(userId,0L);
            Long updatedAmount = expenseUser.getUserExpenseType().equals(UserExpenseType.PAID) ? (hashMap.get(userId) + expenseUser.getAmount()) : (hashMap.get(userId) - expenseUser.getAmount());
            hashMap.put(userId,updatedAmount);
        }
        List<Transaction> transactions = new ArrayList<>();
        PriorityQueue<Pair> minHeap = new PriorityQueue<>();
        PriorityQueue<Pair> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for(Long userId: hashMap.keySet()){
            Long amount = hashMap.get(userId);
            if(amount>0) maxHeap.add(new Pair(userId,amount));
            else if(amount<0) minHeap.add(new Pair(userId, amount));
        }
        while(!minHeap.isEmpty() && !maxHeap.isEmpty()){
            Pair paid = maxHeap.poll();
            Pair hasToPay = minHeap.poll();
            Long paidUserId = paid.getUserId();
            Long hasToPayUserId = hasToPay.getUserId();
            Long paidAmount = paid.getAmount();
            Long hasToPayAmount = (-1)*hasToPay.getAmount();
            if(paidAmount>hasToPayAmount){
                transactions.add(new Transaction(hasToPayUserId, paidUserId, hasToPayAmount));
                maxHeap.add(new Pair(paidUserId, paidAmount-hasToPayAmount));
            }else if(paidAmount<hasToPayAmount){
                transactions.add(new Transaction(hasToPayUserId, paidUserId, paidAmount));
                minHeap.add(new Pair(hasToPayUserId, (-1)*(hasToPayAmount-paidAmount)));
            }else{
                transactions.add(new Transaction(hasToPayUserId, paidUserId, paidAmount));
            }
        }
        return transactions;
    }
}
@Getter
@Setter
class Pair implements Comparable<Pair>{
    private Long userId;
    private Long amount;

    public Pair(Long userId, Long amount) {
        this.userId = userId;
        this.amount = amount;
    }

    @Override
    public int compareTo(Pair pair) {
        return Long.compare(this.amount, pair.amount);
    }
}
