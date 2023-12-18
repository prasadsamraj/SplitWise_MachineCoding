package com.example.splitwise.strategy;

import java.util.List;

public interface SplitAmountStrategy {
    List<Long> splitAmount(int countOfUsers, List<Long> splits, Long amount);
}
