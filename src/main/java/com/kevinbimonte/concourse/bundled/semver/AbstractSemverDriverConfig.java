package com.kevinbimonte.concourse.bundled.semver;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.resource.IResourceConfig;

public abstract class AbstractSemverDriverConfig<T> implements IResourceConfig {

    @SerializedName("initial_version")
    private String initialVersion;

    private SemVerDriver driver;

    AbstractSemverDriverConfig(SemVerDriver driver) {
        this.driver = driver;
    }

    // TODO: Semver validation
    public T setInitialVersion(String initialVersion) {
        this.initialVersion = initialVersion;

        return getSelf();
    }

    /**
     * @return <T> The instance of self
     */
    protected abstract T getSelf();
}
