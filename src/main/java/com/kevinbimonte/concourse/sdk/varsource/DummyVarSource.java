package com.kevinbimonte.concourse.sdk.varsource;

public class DummyVarSource extends AbstractVarSource {
    private DummyVarSource(String name, IVarSourceConfig config) {
        super(name, VarSourceType.DUMMY, config);
    }
}
