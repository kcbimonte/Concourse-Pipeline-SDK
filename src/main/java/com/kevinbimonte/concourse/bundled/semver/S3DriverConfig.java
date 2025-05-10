package com.kevinbimonte.concourse.bundled.semver;

import com.google.gson.annotations.SerializedName;

public class S3DriverConfig extends AbstractSemverDriverConfig<S3DriverConfig> {

    private S3DriverConfig() {
        super(SemVerDriver.S3);
    }

    @Override
    protected S3DriverConfig getSelf() {
        return this;
    }
}
