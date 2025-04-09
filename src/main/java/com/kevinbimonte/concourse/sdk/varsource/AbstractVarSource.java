package com.kevinbimonte.concourse.sdk.varsource;

public abstract class AbstractVarSource {
    private final String name;
    private final VarSourceType type;
    private final IVarSourceConfig config;

    protected AbstractVarSource(String name, VarSourceType type, IVarSourceConfig config) {
        this.name = name;
        this.type = type;
        this.config = config;
    }
}
