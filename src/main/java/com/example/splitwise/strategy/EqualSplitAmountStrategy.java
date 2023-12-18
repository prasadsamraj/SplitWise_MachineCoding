package com.example.splitwise.strategy;

import java.util.ArrayList;
import java.util.List;

public class EqualSplitAmountStrategy implements SplitAmountStrategy{
    @Override
    public List<Long> splitAmount(int countOfUsers, List<Long> splits, Long amount) {
        List<Long> splitAmounts = new ArrayList<>();
        for(int i=0; i<countOfUsers; i++){
            splitAmounts.add(amount/countOfUsers);
        }
        Long checkSplitSum = splitAmounts.stream().reduce(Long::sum).get();
        if(!checkSplitSum.equals(amount)) splitAmounts.set(0, splitAmounts.get(0)+(amount-checkSplitSum));
        return splitAmounts;
    }
}
