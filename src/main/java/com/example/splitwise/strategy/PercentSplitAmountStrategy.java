package com.example.splitwise.strategy;

import java.util.ArrayList;
import java.util.List;

public class PercentSplitAmountStrategy implements SplitAmountStrategy{
    public List<Long> splitAmount(int countOfUsers, List<Long> splits, Long amount) {
        List<Long> splitAmounts = new ArrayList<>();
        for (Long split : splits) {
            splitAmounts.add((amount * split) / 100);
        }
        Long checkSplitSum = splitAmounts.stream().reduce(Long::sum).get();
        if(!checkSplitSum.equals(amount)) splitAmounts.set(0, splitAmounts.get(0)+(amount-checkSplitSum));
        return splitAmounts;
    }
}
