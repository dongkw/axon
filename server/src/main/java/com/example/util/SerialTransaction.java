package com.example.util;

import java.util.Objects;

/**
 * @Author dongkw
 * @Date 2021/1/25、4:24 下午
 * 如果后一个事务依赖前一个事务的返回值该怎么办？？？
 **/
public class SerialTransaction extends TransactionGroup {


    int curIndex;

    @Override
    public SagaStatus getStatus() {
        if (Objects.equals(transactions.get(0).getStatus(), SagaStatus.FAIL)) {
            return SagaStatus.FAIL;
        } else if (Objects.equals(transactions.get(transactions.size() - 1).getStatus(), SagaStatus.SUCCESS)) {
            return SagaStatus.SUCCESS;
        } else {
            return null;
        }
    }

    @Override
    public void start() {
        transactions.get(curIndex).start();
    }

    @Override
    public void rollback() {
        transactions.get(curIndex).rollback();
    }

    @Override
    public void eventHandler(Object event) {
        ITransaction transaction = transactions.get(curIndex);
        transaction.eventHandler(event);
        if (transaction.getStatus().equals(SagaStatus.SUCCESS)) {
            if (curIndex < transactions.size() - 1) {
                transactions.get(++curIndex).start();
            }
        } else {
            if (curIndex > 0) {
                transactions.get(--curIndex).rollback();
            }
        }
    }

}
