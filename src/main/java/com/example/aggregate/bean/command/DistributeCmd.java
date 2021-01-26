package com.example.aggregate.bean.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
public class DistributeCmd {

    @TargetAggregateIdentifier
    private String id;
}
