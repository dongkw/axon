package com.example.aggregate.bean.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * @Author dongkw
 * @Date 2021/1/25、1:28 下午
 **/
@Data
public class CancelledCmd {
    @TargetAggregateIdentifier
    private String id;
}
