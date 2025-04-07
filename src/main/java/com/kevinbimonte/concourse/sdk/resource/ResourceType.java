package com.kevinbimonte.concourse.sdk.resource;

import com.kevinbimonte.concourse.sdk.resource.get.Get;
import com.kevinbimonte.concourse.sdk.resource.put.Put;
import com.kevinbimonte.concourse.sdk.util.Validator;
import lombok.Getter;
import lombok.Setter;

/**
 * Each resource in a pipeline has a type. The resource's type determines what versions are detected, the bits
 * that are fetched when the resource's {@link Get} step runs, and the side effect
 * that occurs when the resource's {@link Put} step runs.
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
