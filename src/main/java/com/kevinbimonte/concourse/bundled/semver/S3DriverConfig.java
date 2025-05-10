package com.kevinbimonte.concourse.bundled.semver;

public class S3DriverConfig extends AbstractSemverDriverConfig<S3DriverConfig> {

    private S3DriverConfig() {
        super(SemverDriver.S3);
    }

    @Override
    protected S3DriverConfig getSelf() {
        return this;
    }
}
