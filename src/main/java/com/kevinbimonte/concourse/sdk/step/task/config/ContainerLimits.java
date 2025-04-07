package com.kevinbimonte.concourse.sdk.step.task.config;

import lombok.Getter;

@Getter
public class ContainerLimits {
    private Integer cpu;
    private Integer memory;

    public ContainerLimits() {
    }

    public ContainerLimits setCPU(Integer cpu) {
        this.cpu = cpu;

        return this;
    }

    public ContainerLimits setMemory(Integer memory) {
        this.memory = memory;

        return this;
    }
}
