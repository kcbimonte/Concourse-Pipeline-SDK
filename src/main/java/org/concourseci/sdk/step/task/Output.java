package org.concourseci.sdk.step.task;

import lombok.Getter;
import org.concourseci.sdk.util.Validator;

public class Output {
    @Getter
    private final String name;

    private String path;

    private Output(String name) {
        Validator.validateIdentifier(name);

        this.name = name;
    }

    public static Output create(String name) {
        return new Output(name);
    }

    public Output setPath(String path) {
        // TODO: Validate Dir-Path

        this.path = path;

        return this;
    }
}
