package com.example.util;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author dongkw
 * @Date 2021/1/26、2:33 下午
 **/
@Component
public class CmdGatewayFactory {


    private static CommandGateway gateway;

    @Autowired
    private void setCommandGateway(CommandGateway commandGateway) {
        gateway = commandGateway;
    }

    public static CommandGateway getCommandGateway() {
        return gateway;
    }
}
