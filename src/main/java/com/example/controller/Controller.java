package com.example.controller;

import com.example.aggregate.bean.event.CancelEvent;
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
public class Controller {

    @Autowired
    private EventGateway eventGateway;

    @PostMapping("/create")
    public ResponseEntity create() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity update() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel")
    public ResponseEntity cancel(@RequestBody CancelEvent cancelEvent) {
        eventGateway.publish(cancelEvent);
        return ResponseEntity.ok().build();
    }

}
