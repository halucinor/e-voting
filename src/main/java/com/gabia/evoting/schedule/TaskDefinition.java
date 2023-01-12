package com.gabia.evoting.schedule;

import lombok.Data;

@Data
public class TaskDefinition {

    private String cronExpression;
    private String data;
    private Long id;
}
