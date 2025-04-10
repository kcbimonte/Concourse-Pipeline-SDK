package com.kevinbimonte.concourse.sdk.varsource.ssm;

import com.kevinbimonte.concourse.sdk.varsource.IVarSourceConfig;

record SSMVarConfig(String region) implements IVarSourceConfig {
}
