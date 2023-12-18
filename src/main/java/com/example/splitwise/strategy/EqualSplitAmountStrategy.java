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
        return splitAmounts;
    }
}
