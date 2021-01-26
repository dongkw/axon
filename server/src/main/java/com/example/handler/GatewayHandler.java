package com.example.handler;

import com.example.command.CmplCmd;
import com.example.command.CmplRollbackCmd;
import com.example.command.VerfCmd;
import com.example.command.VerfRollbackCmd;
import com.example.event.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author dongkw
 * @Date 2021/1/26、11:23 上午
 **/
@Slf4j
@Component
public class GatewayHandler {

    @Autowired
    private EventGateway eventGateway;


    @CommandHandler
    public void handler(VerfCmd cmd) {
        log.debug("receive,{}", cmd);
        if (cmd.getId().endsWith("A")) {
            VerfSuccEvt evt = new VerfSuccEvt();
            evt.setId(cmd.getId());
            eventGateway.publish(evt);
            log.debug("send,{}", evt);
        } else {
            VerfFailEvt evt = new VerfFailEvt();
            evt.setId(cmd.getId());
            eventGateway.publish(evt);
            log.debug("send,{}", evt);
        }
    }

    @CommandHandler
    public void handler(VerfRollbackCmd cmd) {
        log.debug("receive,{}", cmd);
        VerfRollbackEvt event = new VerfRollbackEvt();
        event.setId(cmd.getId());
        eventGateway.publish(event);
        log.debug("send,{}", event);
    }

    @CommandHandler
    public void handler(CmplCmd cmd) {
        log.debug("receive {}", cmd);
        if (cmd.getId().startsWith("A")) {
            CmplSuccEvt evt = new CmplSuccEvt();
            evt.setId(cmd.getId());
            eventGateway.publish(evt);
            log.debug("send {}", evt);
        } else {
            CmplFailEvt evt = new CmplFailEvt();
            evt.setId(cmd.getId());
            eventGateway.publish(evt);
            log.debug("send {}", evt);
        }
    }

    @CommandHandler
    public void handler(CmplRollbackCmd cmd) {
        log.debug("receive {}", cmd);
        CmplRollbackEvt evt = new CmplRollbackEvt();
        evt.setId(cmd.getId());
        eventGateway.publish(evt);
        log.debug("send {}", evt);
    }
}
