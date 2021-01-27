package com.example.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author dongkw
 * @Date 2021/1/25、4:24 下午
 **/
public class ParallelTransaction extends TransactionGroup {


    public Map<Class, ITransaction> getMap() {
        Map<Class, ITransaction> map = new HashMap<>();
        transactions.forEach(t -> t.getEventRegList().forEach(e -> map.put(e, t)));
        return map;
    }

    @Override
    public SagaStatus getStatus() {

        if (transactions.stream().allMatch(t -> Objects.equals(t.getStatus(), SagaStatus.SUCCESS))) {
            return SagaStatus.SUCCESS;
        } else if (transactions.stream().allMatch(t -> Objects.equals(t.getStatus(), SagaStatus.FAIL))) {
            return SagaStatus.FAIL;
        } else {
            return null;
        }
    }

    @Override
    public void start() {
        transactions.forEach(ITransaction::start);
    }

    @Override
    public void rollback() {
        transactions.stream()
                .filter(t -> Objects.equals(t.getStatus(), SagaStatus.SUCCESS))
                .forEach(ITransaction::rollback);
    }

    @Override
    public void eventHandler(Object event) {
        ITransaction transaction = getMap().get(event.getClass());
        transaction.eventHandler(event);
        if (transactions.stream().allMatch(t -> Objects.nonNull(t.getStatus()))) {
            if (transactions.stream().anyMatch(t -> Objects.equals(t.getStatus(), SagaStatus.FAIL))) {
                rollback();
            }
        }
    }

}
