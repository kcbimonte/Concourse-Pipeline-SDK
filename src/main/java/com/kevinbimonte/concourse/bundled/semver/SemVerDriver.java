package com.kevinbimonte.concourse.bundled.semver;

import com.kevinbimonte.concourse.sdk.ISerializableEnum;
import lombok.Getter;

@Getter
public enum SemVerDriver implements ISerializableEnum {
    GIT("git"),
    S3("s3"),
    SWIFT("swift"),
    GCS("gcs");

    private final String displayName;

    SemVerDriver(String displayName) {
        this.displayName = displayName;
    }
}
