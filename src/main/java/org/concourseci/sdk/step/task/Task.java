package org.concourseci.sdk.step.task;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.concourseci.sdk.resource.get.Get;
import org.concourseci.sdk.step.AbstractStep;
import org.concourseci.sdk.step.IStep;
import org.concourseci.sdk.step.task.config.TaskConfig;
import org.concourseci.sdk.util.Validator;

@Getter
public class Task extends AbstractStep<Task> implements IStep {
    private final String task;
    private JsonObject vars;
    private TaskConfig config;
    private String file;
    private String image;
    @Setter
    private Boolean privileged = false;

    public Task(String name, TaskConfig config) {
        Validator.validateIdentifier(name);
        this.task = name;

        this.config = config;
    }

    public Task(String name, Get get, String path) {
        Validator.validateIdentifier(name);
        this.task = name;

        if (path != null && path.startsWith("/")) {
            path = path.trim().substring(1);
        }

        this.file = String.format("%s/%s", get.getIdentifier(), path);
    }

    public Task setImage(Get image) {
        this.image = image.getIdentifier();

        return this;
    }

    public Task addVar(String key, String value) {
        if (vars == null) vars = new JsonObject();

        vars.addProperty(key, value);

        return this;
    }

    public Task addVar(String key, JsonObject value) {
        if (vars == null) vars = new JsonObject();

        vars.add(key, value);

        return this;
    }

    @Override
    protected Task getSelf() {
        return this;
    }
}
