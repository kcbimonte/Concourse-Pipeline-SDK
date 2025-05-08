package com.kevinbimonte.concourse.bundled.semver;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.resource.IResourceConfig;
import com.kevinbimonte.concourse.sdk.resource.IVersion;

public abstract class AbstractSemverDriverConfig<T> implements IResourceConfig {
    @SerializedName("initial_version")
    private IVersion initialVersion;

    private SemVerDriver driver;

    AbstractSemverDriverConfig(SemVerDriver driver) {
        this.driver = driver;
    }

    public T setInitialVersion(IVersion initialVersion) {
        this.initialVersion = initialVersion;

        return getSelf();
    }

    /**
     * @return <T> The instance of self
     */
    protected abstract T getSelf();
}
