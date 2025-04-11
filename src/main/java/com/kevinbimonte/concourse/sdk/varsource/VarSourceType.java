package com.kevinbimonte.concourse.sdk.varsource;

import com.kevinbimonte.concourse.sdk.ISerializableEnum;
import lombok.Getter;

@Getter
public enum VarSourceType implements ISerializableEnum {

    VAULT("vault"),
    SSM("ssm"),
    DUMMY("dummy");

    private final String displayName;

    VarSourceType(String displayName) {
        this.displayName = displayName;
    }
}
