package com.kevinbimonte.concourse.sdk.varsource.ssm;

import com.kevinbimonte.concourse.sdk.varsource.AbstractVarSource;
import com.kevinbimonte.concourse.sdk.varsource.IVarSourceConfig;
import com.kevinbimonte.concourse.sdk.varsource.VarSourceType;

public class SSMVarSource extends AbstractVarSource {
    private SSMVarSource(String name, IVarSourceConfig config) {
        super(name, VarSourceType.SSM, config);
    }
}
