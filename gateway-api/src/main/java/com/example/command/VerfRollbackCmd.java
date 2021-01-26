package com.example.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * @Author dongkw
 * @Date 2021/1/25、3:37 下午
 **/
@Data
public class VerfRollbackCmd {
    @TargetAggregateIdentifier
    private String id;
}
