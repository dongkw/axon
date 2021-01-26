package com.example.saga;

import com.example.aggregate.bean.command.FailCmd;
import com.example.aggregate.bean.command.UpdateConfirmCmd;
import com.example.aggregate.bean.event.UpdateEvent;
import com.example.command.CmplCmd;
import com.example.command.CmplRollbackCmd;
import com.example.command.VerfCmd;
import com.example.command.VerfRollbackCmd;
import com.example.event.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author dongkw
 * @Date 2021/1/25、11:15 上午
 **/
@Saga
public class UpdateSaga {

    private UpdateEvent vo;

    @Autowired
    private transient CommandGateway commandGateway;

    private Boolean cmplFlag;
    private Boolean verfFlag;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void startSaga(UpdateEvent event) {

        this.vo = event;
        CmplCmd cmd = new CmplCmd();
        cmd.setId(event.getId());
        commandGateway.send(cmd);
        VerfCmd cmd1 = new VerfCmd();
        cmd1.setId(event.getId());
        commandGateway.send(cmd1);

    }

    @SagaEventHandler(associationProperty = "id")
    public void handler(CmplFailEvt evt) {
        this.cmplFlag = false;
        end();
    }

    @SagaEventHandler(associationProperty = "id")
    public void handler(CmplSuccEvt evt) {
        this.cmplFlag = true;
        end();
    }

    @SagaEventHandler(associationProperty = "id")
    public void handler(CmplRollbackEvt evt) {
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "id")
    public void handler(VerfFailEvt evt) {
        this.verfFlag = false;
        end();
    }

    @SagaEventHandler(associationProperty = "id")
    public void handler(VerfSuccEvt evt) {
        this.verfFlag = true;
        end();
    }

    @SagaEventHandler(associationProperty = "id")
    public void handler(VerfRollbackEvt evt) {
        SagaLifecycle.end();
    }

    private void end() {
        if (cmplFlag != null && verfFlag != null) {
            if (cmplFlag && verfFlag) {
                UpdateConfirmCmd cmd = new UpdateConfirmCmd();
                cmd.setId(this.vo.getId());
                commandGateway.send(cmd);
            } else if (cmplFlag && !verfFlag) {
                CmplRollbackCmd cmd = new CmplRollbackCmd();
                cmd.setId(this.vo.getId());
                commandGateway.send(cmd);
            } else if (!cmplFlag && verfFlag) {
                VerfRollbackCmd cmd = new VerfRollbackCmd();
                cmd.setId(this.vo.getId());
                commandGateway.send(cmd);
            } else if (!cmplFlag && !verfFlag) {
                FailCmd cmd = new FailCmd();
                cmd.setId(this.vo.getId());
                commandGateway.send(cmd);
            }
        }

    }

}
