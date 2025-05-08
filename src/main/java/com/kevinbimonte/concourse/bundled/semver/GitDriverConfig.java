package com.kevinbimonte.concourse.bundled.semver;

public class GitDriverConfig extends AbstractSemverDriverConfig<GitDriverConfig> {

    private GitDriverConfig() {
        super(SemVerDriver.GIT);
    }

    @Override
    protected GitDriverConfig getSelf() {
        return this;
    }
}
