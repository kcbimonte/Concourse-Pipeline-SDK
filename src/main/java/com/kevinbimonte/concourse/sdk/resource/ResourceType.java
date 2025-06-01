package com.kevinbimonte.concourse.sdk.resource;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.resource.get.Get;
import com.kevinbimonte.concourse.sdk.resource.get.IGetConfig;
import com.kevinbimonte.concourse.sdk.resource.put.Put;
import com.kevinbimonte.concourse.sdk.util.Validator;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * Each resource in a pipeline has a type. The resource's type determines what versions are detected, the bits
 * that are fetched when the resource's {@link Get} step runs, and the side effect
 * that occurs when the resource's {@link Put} step runs.
 */
public abstract class ResourceType<T extends ResourceType<T, U>, U extends IResourceConfig> {

    @Getter
    private final String name;

    private String type = "registry-image";

    private U source;

    private Boolean privileged;

    private IGetConfig params;

    @SerializedName("check_every")
    private String checkEvery;

    private Set<String> tags;

    private U defaults;

    protected ResourceType(String name) {
        Validator.validateIdentifier(name);

        this.name = name;
    }

    protected ResourceType(String name, U source) {
        Validator.validateIdentifier(name);

        this.name = name;
        this.source = source;
    }

    public T markPrivileged() {
        this.privileged = true;

        return getSelf();
    }

    public T setParams(IGetConfig params) {
        this.params = params;

        return getSelf();
    }

    public T setCheckEvery(String duration) {
        Validator.validateDuration(duration);
        this.checkEvery = duration;

        return getSelf();
    }

    public T addTag(String tag) {
        if (this.tags == null) {
            this.tags = new HashSet<>();
        }

        this.tags.add(tag);

        return getSelf();
    }

    public T setDefaults(U defaults) {
        this.defaults = defaults;

        return getSelf();
    }

    /**
     * @return <T> The instance of self
     */
    protected abstract T getSelf();

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        return ((ResourceType) obj).name.equals(this.name);
    }
}
