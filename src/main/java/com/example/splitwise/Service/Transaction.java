package com.example.splitwise.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private Long fromUserId;
    private Long toUserId;
    private Long amount;

    public Transaction(Long fromUserId, Long toUserId, Long amount) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
    }
}
