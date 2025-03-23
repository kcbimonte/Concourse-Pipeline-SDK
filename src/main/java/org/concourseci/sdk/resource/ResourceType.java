package org.concourseci.sdk.resource;

import lombok.Getter;
import lombok.Setter;
import org.concourseci.sdk.util.Validator;

/**
 * Each resource in a pipeline has a type. The resource's type determines what versions are detected, the bits
 * that are fetched when the resource's {@link org.concourseci.sdk.resource.get.Get} step runs, and the side effect
 * that occurs when the resource's {@link org.concourseci.sdk.resource.put.Put} step runs.
 */
public abstract class ResourceType {
    @Getter
    private final String name;

    @Setter
    private String type = "registry-image";

    protected ResourceType(String name) {
        Validator.validateIdentifier(name);

        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        return ((ResourceType) obj).name.equals(this.name);
    }
}
