package org.concourseci.sdk.resource;

import lombok.Getter;
import lombok.Setter;
import org.concourseci.sdk.util.Validator;


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
