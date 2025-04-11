package com.kevinbimonte.concourse.sdk.step.var;

import com.kevinbimonte.concourse.sdk.ISerializableEnum;
import lombok.Getter;

@Getter
public enum VarFormat implements ISerializableEnum {
    JSON("json"),
    YAML("yaml"),
    YML("yml"),
    TRIM("trim"),
    RAW("raw");

    private final String displayName;

    VarFormat(String displayName) {
        this.displayName = displayName;
    }
}
