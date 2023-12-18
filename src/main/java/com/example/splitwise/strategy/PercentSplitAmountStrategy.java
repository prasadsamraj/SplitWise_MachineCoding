package com.example.splitwise.strategy;

import java.util.ArrayList;
import java.util.List;

public class PercentSplitAmountStrategy implements SplitAmountStrategy{
    public List<Long> splitAmount(int countOfUsers, List<Long> splits, Long amount) {
        List<Long> splitAmounts = new ArrayList<>();
        for (Long split : splits) {
            splitAmounts.add((amount * split) / 100);
        }
        return splitAmounts;
    }
}
