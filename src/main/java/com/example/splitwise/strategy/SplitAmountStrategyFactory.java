package com.example.splitwise.strategy;

import com.example.splitwise.commands.SplitType;

public class SplitAmountStrategyFactory {
    public static SplitAmountStrategy getSplitAmountStrategyFactory(SplitType splitType){
        if(splitType.equals(SplitType.Equal)) return new EqualSplitAmountStrategy();
        else if(splitType.equals(SplitType.Exact)) return new ExactSplitAmountStrategy();
        else if(splitType.equals(SplitType.Percent)) return new PercentSplitAmountStrategy();
        else return new RatioSplitAmountStrategy();
    }
}
