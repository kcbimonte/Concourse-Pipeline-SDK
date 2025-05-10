package com.kevinbimonte.concourse.bundled.semver;

import com.kevinbimonte.concourse.sdk.ISerializableEnum;
import lombok.Getter;

@Getter
public enum SemverDriver implements ISerializableEnum {
    GIT("git"),
    S3("s3"),
    SWIFT("swift"),
    GCS("gcs");

    private final String displayName;

    SemverDriver(String displayName) {
        this.displayName = displayName;
    }
}
