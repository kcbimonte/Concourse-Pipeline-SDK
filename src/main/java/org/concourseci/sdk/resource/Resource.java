package org.concourseci.sdk.resource;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import org.concourseci.sdk.resource.get.Get;
import org.concourseci.sdk.resource.get.NoOpGet;
import org.concourseci.sdk.resource.put.NoOpPut;
import org.concourseci.sdk.resource.put.Put;
import org.concourseci.sdk.util.Validator;

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
