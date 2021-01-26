package com.example.saga;

import com.example.aggregate.bean.command.ConfirmCmd;
import com.example.aggregate.bean.command.FailCmd;
import com.example.aggregate.bean.event.CreateEvent;
import com.example.command.CmplCmd;
import com.example.event.CmplFailEvt;
import com.example.event.CmplSuccEvt;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
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
public class CreateSaga {

    private CreateEvent createEvent;

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void startSaga(CreateEvent event) {

        this.createEvent = event;
        CmplCmd cmd = new CmplCmd();
        cmd.setId(event.getId());
        commandGateway.send(cmd);

    }
    @EndSaga
    @SagaEventHandler(associationProperty = "id")
    public void handler(CmplFailEvt evt) {
        FailCmd cmd = new FailCmd();
        cmd.setId(this.createEvent.getId());
        commandGateway.send(cmd);
    }


    @SagaEventHandler(associationProperty = "id")
    public void handler(CmplSuccEvt evt) {
        ConfirmCmd cmd = new ConfirmCmd();
        cmd.setId(evt.getId());
        commandGateway.send(cmd);
        SagaLifecycle.end();
    }

//    @SagaEventHandler(associationProperty = "id")
//    public void handler(CmplRollbackEvt evt) {
//        FailCmd cmd = new FailCmd();
//        cmd.setId(this.createEvent.getId());
//        commandGateway.send(cmd);
//
//    }
}
