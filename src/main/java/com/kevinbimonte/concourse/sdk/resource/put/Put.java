package com.kevinbimonte.concourse.sdk.resource.put;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.resource.Resource;
import com.kevinbimonte.concourse.sdk.resource.get.Get;
import com.kevinbimonte.concourse.sdk.resource.get.IGetConfig;
import com.kevinbimonte.concourse.sdk.step.AbstractStep;
import com.kevinbimonte.concourse.sdk.step.IStep;
import com.kevinbimonte.concourse.sdk.step.task.config.Output;
import com.kevinbimonte.concourse.sdk.util.Validator;
import lombok.Getter;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

@Getter
public abstract class Put extends AbstractStep<Put> implements IStep {

    @SerializedName("put")
    private final String identifier;

    private String resource;

    private IPutConfig params;

    @JsonAdapter(InputsSerializer.class)
    private Set<String> inputs;

    @SerializedName("get_params")
    private IGetConfig getParams;

    @SerializedName("no_get")
    private Boolean noGet;

    public Put(Resource resource) {
        this.identifier = resource.getName();
    }

    public Put(Resource resource, String name) {
        Validator.validateIdentifier(name);

        this.identifier = name;
        this.resource = resource.getName();
    }

    public Put setAllInputs() {
        if (this.inputs == null) {
            this.inputs = new HashSet<>();
        }

        if (!this.inputs.isEmpty()) {
            this.inputs.clear();
        }

        this.inputs.add("all");

        return this;
    }

    public Put setDetectInputs() {
        if (this.inputs == null) {
            this.inputs = new HashSet<>();
        }

        if (!this.inputs.isEmpty()) {
            this.inputs.clear();
        }

        this.inputs.add("detect");

        return this;
    }

    public Put addInput(Output output) {
        if (this.inputs == null) {
            this.inputs = new HashSet<>();
        }

        this.inputs.add(output.getName());

        return this;
    }

    public Put addInput(Get get) {
        if (this.inputs == null) {
            this.inputs = new HashSet<>();
        }

        this.inputs.add(get.getIdentifier());

        return this;
    }

    public Put setParams(IPutConfig config) {
        this.params = config;

        return this;
    }

    public Put setGetParams(IGetConfig config) {
        if (this.noGet != null && this.noGet) {
            throw new IllegalArgumentException("Cannot set get params when get is disabled");
        }

        this.getParams = config;

        return this;
    }

    public Put disableGet() {
        if (this.getParams != null) {
            throw new IllegalArgumentException("Cannot disable get when params are set");
        }

        this.noGet = true;

        return this;
    }

    @Override
    protected Put getSelf() {
        return this;
    }

    private static class InputsSerializer implements JsonSerializer<Set<String>> {

        @Override
        public JsonElement serialize(Set<String> src, Type typeOfSrc, JsonSerializationContext context) {

            if (src.size() == 1 && src.contains("all")) {
                return new JsonPrimitive("all");
            }

            if (src.size() == 1 && src.contains("detect")) {
                return new JsonPrimitive("detect");
            }

            if (!src.isEmpty()) {
                JsonArray arr = new JsonArray();

                src.stream().map(JsonPrimitive::new).forEach(arr::add);

                return arr;
            }

            return null;
        }
    }
}
