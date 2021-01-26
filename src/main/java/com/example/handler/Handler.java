package com.example.handler;

import com.example.aggregate.bean.command.AprvPassCmd;
import com.example.aggregate.bean.command.DistributeCmd;
import com.example.event.AprvPassApiEvent;
import com.example.event.DistributeApiEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author dongkw
 * @Date 2021/1/25、11:26 上午
 **/
@Component
public class Handler {

    @Autowired
    private CommandGateway commandGateway;

    @EventHandler
    public void handler(AprvPassApiEvent evt) {
        AprvPassCmd cmd = new AprvPassCmd();
        cmd.setId(evt.getId());
        commandGateway.send(cmd);
    }

    @EventHandler
    public void handler(DistributeApiEvent evt) {
        DistributeCmd cmd = new DistributeCmd();
        cmd.setId(evt.getId());
        commandGateway.send(cmd);
    }
}
