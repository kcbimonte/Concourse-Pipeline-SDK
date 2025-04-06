package com.kevinbimonte.concourse.sdk.resource.get;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.resource.IVersion;
import lombok.Getter;
import com.kevinbimonte.concourse.sdk.job.Job;
import com.kevinbimonte.concourse.sdk.resource.Resource;
import com.kevinbimonte.concourse.sdk.step.AbstractStep;
import com.kevinbimonte.concourse.sdk.step.IStep;
import com.kevinbimonte.concourse.sdk.util.Validator;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Get extends AbstractStep<Get> implements IStep {

    @SerializedName("get")
    private final String identifier;

    private String resource = null;

    @SerializedName("passed")
    private final List<String> passedJobs = new ArrayList<>();

    @SerializedName("params")
    private IGetConfig config;

    private Boolean trigger = false;

    @SerializedName("version")
    private JsonElement version;

    private final transient Gson gson = new GsonBuilder().create();

    public Get(Resource resource) {
        this.identifier = resource.getName();
    }

    public Get(Resource resource, String name) {
        Validator.validateIdentifier(name);

        this.identifier = name;
        this.resource = resource.getName();
    }

    public Get enableTrigger() {
        this.trigger = true;

        return this;
    }

    public Get addPassedRequirement(Job job) {
        this.passedJobs.add(job.getName());

        return this;
    }

    public Get setConfig(IGetConfig config) {
        this.config = config;

        return this;
    }

    public Get setLatestVersion() {
        this.version = new JsonPrimitive("latest");

        return this;
    }

    public Get setEveryVersion() {
        this.version = new JsonPrimitive("every");

        return this;
    }

    public Get setVersion(IVersion version) {
        this.version = gson.toJsonTree(version);

        return this;
    }

    @Override
    protected Get getSelf() {
        return this;
    }
}
