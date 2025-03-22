package org.concourseci.sdk.step.task;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.concourseci.sdk.resource.get.Get;
import org.concourseci.sdk.step.IStep;
import org.concourseci.sdk.util.Validator;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Task implements IStep {
    private final String task;

    private TaskConfig config;
    private String file;
    private String image;

    @Setter
    private Boolean privileged = false;

    private final Map<String, Object> vars = new HashMap<>();

    @SerializedName("ensure")
    private IStep ensure;

    @SerializedName("on_abort")
    private IStep onAbort;

    @SerializedName("on_error")
    private IStep onError;

    @SerializedName("on_failure")
    private IStep onFailure;

    @SerializedName("on_success")
    private IStep onSuccess;

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

    public Task addVar(String name, String value) {
        this.vars.put(name, value);

        return this;
    }

    public Task setEnsure(IStep step) {
        this.ensure = step;

        return this;
    }

    public Task setOnAbort(IStep step) {
        this.onAbort = step;

        return this;
    }

    public Task setOnError(IStep step) {
        this.onError = step;

        return this;
    }

    public Task setOnFailure(IStep step) {
        this.onFailure = step;

        return this;
    }

    public Task setOnSuccess(IStep step) {
        this.onSuccess = step;

        return this;
    }
}
