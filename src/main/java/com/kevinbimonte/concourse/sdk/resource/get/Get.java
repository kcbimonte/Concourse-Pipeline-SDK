package com.kevinbimonte.concourse.sdk.resource.get;

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
public abstract class Get<T extends IVersion> extends AbstractStep<Get<T>> implements IStep {

    @SerializedName("get")
    private final String identifier;

    private String resource = null;

    @SerializedName("passed")
    private final List<String> passedJobs = new ArrayList<>();

    @SerializedName("params")
    private IGetConfig config;

    private Boolean trigger = false;

    @SerializedName("version")
    protected String strVersion;

    @SerializedName("version")
    protected T specificVersion;

    public Get(Resource resource) {
        this.identifier = resource.getName();
    }

    public Get(Resource resource, String name) {
        Validator.validateIdentifier(name);

        this.identifier = name;
        this.resource = resource.getName();
    }

    public Get<T> enableTrigger() {
        this.trigger = true;

        return this;
    }

    public Get<T> addPassedRequirement(Job job) {
        this.passedJobs.add(job.getName());

        return this;
    }

    public Get<T> setConfig(IGetConfig config) {
        this.config = config;

        return this;
    }

    public Get<T> setLatestVersion() {
        this.strVersion = "latest";
        this.specificVersion = null;

        return this;
    }

    public Get<T> setEveryVersion() {
        this.strVersion = "every";
        this.specificVersion = null;

        return this;
    }

    public abstract Get<T> setSpecificVersion(T version);

    @Override
    protected Get<T> getSelf() {
        return this;
    }
}
