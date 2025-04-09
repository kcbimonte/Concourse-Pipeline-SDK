package com.kevinbimonte.concourse.sdk.varsource.ssm;

import com.kevinbimonte.concourse.sdk.util.Validator;
import com.kevinbimonte.concourse.sdk.varsource.AbstractVarSource;
import com.kevinbimonte.concourse.sdk.varsource.VarSourceType;

public class SSMVarSource extends AbstractVarSource<SSMVarConfig> {
    private SSMVarSource(String name, SSMVarConfig config) {
        super(name, VarSourceType.SSM, config);
    }

    public static SSMVarSource create(String name, String region) {
        Validator.validateIdentifier(name);

        if (region == null) {
            throw new IllegalArgumentException("Config cannot be null");
        }

        SSMVarConfig config = new SSMVarConfig(region);

        return new SSMVarSource(name, config);
    }
}