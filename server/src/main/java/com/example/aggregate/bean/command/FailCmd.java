package com.example.aggregate.bean.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * @Author dongkw
 * @Date 2021/1/25、3:27 下午
 **/
@Data
public class FailCmd {
    @TargetAggregateIdentifier
    private String id;
}
