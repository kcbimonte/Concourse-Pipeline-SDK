package org.concourseci.sdk.resource;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import org.concourseci.sdk.job.Job;
import org.concourseci.sdk.step.IStep;
import org.concourseci.sdk.util.Validator;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Get implements IStep {

    private String resource = null;

    @SerializedName("get")
    private final String identifier;

    private Boolean trigger = false;

    @SerializedName("passed")
    private final List<String> passedJobs = new ArrayList<>();

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
}
