package com.example.util;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author dongkw
 * @Date 2021/1/25、4:20 下午
 **/
public abstract class TransactionGroup implements ITransaction {

    protected List<ITransaction> transactions;

    public void setTransactions(List<ITransaction> transaction) {
        this.transactions = transaction;
    }
    @Override
    public List<Class> getEventRegList() {
        return transactions.stream().flatMap(t -> t.getEventRegList().stream()).collect(Collectors.toList());
    }
    @Override
    public Object fill(Object cmd) {
        transactions.forEach(t -> t.fill(cmd));
        return cmd;
    }
}
