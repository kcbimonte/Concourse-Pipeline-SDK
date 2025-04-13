package com.kevinbimonte.concourse.sdk.step.task.config;

import com.kevinbimonte.concourse.sdk.ISerializableEnum;
import lombok.Getter;

@Getter
public enum Platform implements ISerializableEnum {

    LINUX("linux"),
    DARWIN("darwin"),
    WINDOWS("windows");

    private final String displayName;

    Platform(String displayName) {
        this.displayName = displayName;
    }
}
