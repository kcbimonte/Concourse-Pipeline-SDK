package org.concourseci.sdk.step.task.config;

import org.concourseci.sdk.resource.get.Get;
import org.concourseci.sdk.util.Validator;

public class Input {
    private final String name;
    private String path;
    private Boolean optional = false;

    private Input(String name) {
        Validator.validateIdentifier(name);

        this.name = name;
    }

    public static Input create(Get get) {
        return new Input(get.getIdentifier());
    }

    public static Input create(Output output) {
        return new Input(output.getName());
    }

    public Input setPath(String path) {
        // TODO: Validate Dir-Path

        this.path = path;

        return this;
    }

    public Input markOptional() {
        this.optional = true;

        return this;
    }
}
