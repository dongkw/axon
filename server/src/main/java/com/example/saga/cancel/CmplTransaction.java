package com.example.saga.cancel;

import com.example.command.CmplCmd;
import com.example.command.CmplRollbackCmd;
import com.example.event.CmplFailEvt;
import com.example.event.CmplRollbackEvt;
import com.example.event.CmplSuccEvt;
import com.example.util.CmdGatewayFactory;
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
public class CmplTransaction extends TransactionUnit {


    private String id;

    public CmplTransaction(String id) {
        this.id = id;
        getEventRegList();
    }

    @Override
    public void start() {
        CmplCmd cmd = new CmplCmd();
        cmd.setId(this.id);
        CmdGatewayFactory.getCommandGateway().send(cmd);
        log.info("send:{}", cmd);
    }

    @Override
    public void rollback() {
        CmplRollbackCmd cmd = new CmplRollbackCmd();
        cmd.setId(this.id);
        CmdGatewayFactory.getCommandGateway().send(cmd);
        log.info("send:{}", cmd);
    }

    @Override
    public void eventHandler(Object event) {
        if (event instanceof CmplSuccEvt) {
            this.sagaStatus = SagaStatus.SUCCESS;
        } else {
            this.sagaStatus = SagaStatus.FAIL;
        }
    }

    @Override
    public List<Class> getEventRegList() {
        List<Class> list = new ArrayList<>();
        list.add(CmplSuccEvt.class);
        list.add(CmplFailEvt.class);
        list.add(CmplRollbackEvt.class);
        return list;
    }

    @Override
    public Object fill(Object cmd) {
        return cmd;
    }
}
