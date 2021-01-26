package com.example.saga.cancel;

import com.example.command.VerfCmd;
import com.example.command.VerfRollbackCmd;
import com.example.event.VerfFailEvt;
import com.example.event.VerfRollbackEvt;
import com.example.event.VerfSuccEvt;
import com.example.util.CommandGatewayFactory;
import com.example.util.SagaStatus;
import com.example.util.TransactionUnit;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author dongkw
 * @Date 2021/1/26、1:32 下午
 **/
@Slf4j
public class VerfTransaction extends TransactionUnit {


    private String id;

    public VerfTransaction(String id) {
        this.id = id;
        getEventRegList();
    }


    @Override
    public void start() {
        VerfCmd cmd = new VerfCmd();
        cmd.setId(this.id);
        CommandGatewayFactory.getCommandGateway().send(cmd);
        log.info("send:{}", cmd);

    }

    @Override
    public void rollback() {
        VerfRollbackCmd cmd = new VerfRollbackCmd();
        cmd.setId(this.id);
        CommandGatewayFactory.getCommandGateway().send(cmd);
        log.info("send:{}", cmd);

    }

    @Override
    public void eventHandler(Object event) {
        if (event instanceof VerfSuccEvt) {
            this.sagaStatus = SagaStatus.SUCCESS;
        } else {
            this.sagaStatus = SagaStatus.FAIL;
        }
    }

    @Override
    public List<Class> getEventRegList() {
        List<Class> list = new ArrayList<>();
        list.add(VerfSuccEvt.class);
        list.add(VerfFailEvt.class);
        list.add(VerfRollbackEvt.class);
        return list;
    }

    @Override
    public Object fill(Object cmd) {
        return cmd;
    }
}
