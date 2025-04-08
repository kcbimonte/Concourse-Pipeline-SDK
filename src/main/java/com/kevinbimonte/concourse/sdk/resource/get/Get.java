package com.kevinbimonte.concourse.sdk.resource.get;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.job.Job;
import com.kevinbimonte.concourse.sdk.resource.IVersion;
import com.kevinbimonte.concourse.sdk.resource.Resource;
import com.kevinbimonte.concourse.sdk.step.AbstractStep;
import com.kevinbimonte.concourse.sdk.step.IStep;
import com.kevinbimonte.concourse.sdk.util.Validator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Get extends AbstractStep<Get> implements IStep {

    @SerializedName("get")
    private final String identifier;

    private String resource = null;

    @SerializedName("passed")
    private List<String> passedJobs;

    @SerializedName("params")
    private IGetConfig config;

    private Boolean trigger;

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
        if (this.passedJobs == null) {
            this.passedJobs = new ArrayList<>();
        }

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

    protected Get setVersion(IVersion version) {
        this.version = gson.toJsonTree(version);

        return this;
    }

    @Override
    protected Get getSelf() {
        return this;
    }
}
