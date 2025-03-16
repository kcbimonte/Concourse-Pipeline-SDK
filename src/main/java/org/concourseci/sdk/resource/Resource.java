package org.concourseci.sdk.resource;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import org.concourseci.sdk.util.Validator;

@Getter
public abstract class Resource {
    private final String name;
    private final String type;
    @SerializedName("source")
    private final IResourceConfig config;

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

    public Get getGetDefinition() {
        return new Get(this);
    }

    public Get getGetDefinition(String identifier) {
        return new Get(this, identifier);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        return ((Resource) obj).name.equals(this.name);
    }
}
