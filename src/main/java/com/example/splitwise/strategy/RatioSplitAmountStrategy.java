package com.example.splitwise.strategy;

import java.util.ArrayList;
import java.util.List;

public class RatioSplitAmountStrategy implements SplitAmountStrategy{

    @Override
    public List<Long> splitAmount(int countOfUsers, List<Long> splits, Long amount) {
        Long totalRatio = splits.stream().reduce(Long::sum).get();
        List<Long> splitAmounts = new ArrayList<>();
        for (Long split : splits) {
            splitAmounts.add((amount * split) / totalRatio);
        }
        Long checkSplitSum = splitAmounts.stream().reduce(Long::sum).get();
        if(!checkSplitSum.equals(amount)) splitAmounts.set(0, splitAmounts.get(0)+(amount-checkSplitSum));
        return splitAmounts;
    }
}
