package com.kevinbimonte.concourse.bundled.semver;

import com.kevinbimonte.concourse.sdk.ISerializableEnum;
import lombok.Getter;

@Getter
public enum SemverBump implements ISerializableEnum {
    MAJOR("major"),
    MINOR("minor"),
    PATCH("patch"),
    FINAL("final");

    private final String displayName;

    SemverBump(String displayName) {
        this.displayName = displayName;
    }
}
