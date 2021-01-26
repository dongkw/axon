package com.example.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * @Author dongkw
 * @Date 2021/1/25、1:49 下午
 **/
@Data
public class CmplRollbackCmd {
    @TargetAggregateIdentifier
    private String id;
}
