package com.example.splitwise.strategy;

import java.util.List;

public class ExactSplitAmountStrategy implements SplitAmountStrategy{
    public List<Long> splitAmount(int countOfUsers, List<Long> splits, Long amount) {
        return splits;
    }
}
