package com.kevinbimonte.concourse.bundled.semver;

public class SwiftDriverConfig extends AbstractSemverDriverConfig<SwiftDriverConfig> {

    private SwiftDriverConfig() {
        super(SemVerDriver.SWIFT);
    }

    @Override
    protected SwiftDriverConfig getSelf() {
        return this;
    }
}
