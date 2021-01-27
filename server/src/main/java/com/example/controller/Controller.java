package com.example.controller;

import com.example.aggregate.bean.command.CreateCmd;
import com.example.aggregate.bean.event.CancelEvent;
import com.example.aggregate.bean.event.CancelledEvent;
import com.example.aggregate.bean.event.FailEvent;
import com.example.util.SyncController;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author dongkw
 * @Date 2021/1/25、11:08 上午
 **/
@RestController
public class Controller extends SyncController {

    @Autowired
    private EventGateway eventGateway;

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody CreateCmd cmd) {
        commandGateway.send(cmd);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity update() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel")
    public ResponseEntity cancel(@RequestBody CancelEvent cancelEvent) {
        eventGateway.publish(cancelEvent);
        Object result = waitResponse(cancelEvent.getId());
        return ResponseEntity.ok(result);
    }

    @EventHandler
    public void handle(CancelledEvent event) {
        syncResponse(event.getId(), event);
    }

    @EventHandler
    public void handle(FailEvent event) {
        syncResponse(event.getId(), event);
    }
}
