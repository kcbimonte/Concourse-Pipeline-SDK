package com.kevinbimonte.concourse.sdk.resource;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.resource.get.Get;
import com.kevinbimonte.concourse.sdk.resource.get.NoOpGet;
import com.kevinbimonte.concourse.sdk.resource.put.NoOpPut;
import com.kevinbimonte.concourse.sdk.resource.put.Put;
import com.kevinbimonte.concourse.sdk.util.Validator;
import lombok.Getter;

/**
 * Resources are the heart and soul of Concourse. They represent all external inputs to and outputs of jobs in the
 * pipeline.
 * <p>
 * Each resource represents a versioned artifact with an external source of truth. Configuring the same resource
 * in any pipeline on any Concourse cluster will behave the exact same way. Concourse will continuously check each
 * configured resource to discover new versions. These versions then flow through the pipeline via get steps
 * configured on Jobs.
 */
@Getter
public abstract class Resource {
    private final String name;
    private final String type;

    @SerializedName("source")
    private final IResourceConfig config;

    private String icon = null;

    @SerializedName("check_every")
    private String checkEvery = null;

    public Resource(String name, ResourceType type, IResourceConfig config) {
        Validator.validateIdentifier(name);

        this.name = name;
        this.type = type.getName();
        this.config = config;
    }

    Resource(ResourceType type, IResourceConfig config) {
        this.name = "";
        this.type = type.getName();
        this.config = config;
    }

    public Get createGetDefinition() {
        return new NoOpGet(this);
    }

    public Get createGetDefinition(String identifier) {
        return new NoOpGet(this, identifier);
    }

    public Put createPutDefinition() {
        return new NoOpPut(this);
    }

    public Put createPutDefinition(String identifier) {
        return new NoOpPut(this, identifier);
    }

    public Resource setIcon(String icon) {
        this.icon = icon;

        return this;
    }

    public Resource setCheckEvery(String duration) {
        if (duration.equals("never")) {
            this.checkEvery = duration;

            return this;
        }

        Validator.validateDuration(duration);
        this.checkEvery = duration;

        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        return ((Resource) obj).name.equals(this.name);
    }
}
