package com.kevinbimonte.concourse.sdk.varsource.dummy;

import com.kevinbimonte.concourse.sdk.util.Validator;
import com.kevinbimonte.concourse.sdk.varsource.AbstractVarSource;
import com.kevinbimonte.concourse.sdk.varsource.VarSourceType;

public class DummyVarSource extends AbstractVarSource<DummyVarConfig> {
    private DummyVarSource(String name, DummyVarConfig config) {
        super(name, VarSourceType.DUMMY, config);
    }

    public static DummyVarSource create(String name) {
        Validator.validateIdentifier(name);

        return new DummyVarSource(name, new DummyVarConfig());
    }
}
