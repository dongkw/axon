package com.example.saga.cancel;

import com.example.aggregate.bean.command.CancelledCmd;
import com.example.aggregate.bean.command.FailCmd;
import com.example.aggregate.bean.event.CancelEvent;
import com.example.event.*;
import com.example.util.ITransaction;
import com.example.util.SagaStatus;
import com.example.util.SerialTransaction;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Author dongkw
 * @Date 2021/1/25、5:00 下午
 **/
@Saga
@Slf4j
public class CancelSaga {

    private ITransaction transaction;

    @Autowired
    private transient CommandGateway commandGateway;

    private CancelEvent vo;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void startSaga(CancelEvent event) {
        this.vo = event;
//        ParallelTransaction transaction = new ParallelTransaction();
        SerialTransaction transaction = new SerialTransaction();

        transaction.setTransactions(Arrays.asList(new CmplTransaction(vo.getId()), new VerfTransaction(vo.getId())));
        this.transaction = transaction;
        this.transaction.start();
    }

    @SagaEventHandler(associationProperty = "id")
    public void handler(CmplFailEvt evt) {
        checkIsFinish(evt);
    }

    @SagaEventHandler(associationProperty = "id")
    public void handler(CmplSuccEvt evt) {
        checkIsFinish(evt);
    }

    @SagaEventHandler(associationProperty = "id")
    public void handler(CmplRollbackEvt evt) {
        checkIsFinish(evt);
    }

    @SagaEventHandler(associationProperty = "id")
    public void handler(VerfFailEvt evt) {
        checkIsFinish(evt);
    }

    @SagaEventHandler(associationProperty = "id")
    public void handler(VerfSuccEvt evt) {
        checkIsFinish(evt);
    }

    @SagaEventHandler(associationProperty = "id")
    public void handler(VerfRollbackEvt evt) {
        checkIsFinish(evt);
    }

    private void checkIsFinish(Object evt) {
        log.info("receive:{}", evt);
        transaction.eventHandler(evt);
        if (Objects.equals(transaction.getStatus(), SagaStatus.SUCCESS)) {
            CancelledCmd cmd = new CancelledCmd();
            cmd.setId(vo.getId());
            transaction.fill(cmd);
            commandGateway.send(cmd);
            log.info("end---send:{}", cmd);
            SagaLifecycle.end();

        } else if (Objects.equals(transaction.getStatus(), SagaStatus.FAIL)) {

            FailCmd cmd = new FailCmd();
            cmd.setId(vo.getId());
            transaction.fill(cmd);
            commandGateway.send(cmd);
            log.info("end---send:{}", cmd);
            SagaLifecycle.end();
        }
    }

}
