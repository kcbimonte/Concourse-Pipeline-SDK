package com.kevinbimonte.concourse.sdk.varsource.vault;

import com.kevinbimonte.concourse.sdk.varsource.AbstractVarSource;
import com.kevinbimonte.concourse.sdk.varsource.VarSourceType;

public class VaultVarSource extends AbstractVarSource {
    private VaultVarSource(String name, VaultVarConfig config) {
        super(name, VarSourceType.VAULT, config);
    }
}
