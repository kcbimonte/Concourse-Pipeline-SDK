package com.kevinbimonte.concourse.sdk.varsource;

import lombok.Getter;

@Getter
public abstract class AbstractVarSource<T extends IVarSourceConfig> {
    private final String name;
    private final VarSourceType type;
    private final T config;

    protected AbstractVarSource(String name, VarSourceType type, T config) {
        this.name = name;
        this.type = type;
        this.config = config;
    }

    public String referenceVariable(String... variable) {
        String argPath = String.format(".%s".repeat(variable.length), (Object[]) variable);

        return String.format("((%s:%s))", this.name, argPath.substring(1));
    }
}
