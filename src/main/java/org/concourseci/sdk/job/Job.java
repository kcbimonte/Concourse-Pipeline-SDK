package org.concourseci.sdk.job;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import org.concourseci.sdk.step.IStep;
import org.concourseci.sdk.util.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Jobs determine the actions of your pipeline. They determine how resources progress through it, and how the pipeline
 * is visualized.
 * <p>
 * The most important attribute of a job is its build plan, configured as job.plan. This determines the sequence of
 * Steps to execute in any builds of the job.
 *
 * @see org.concourseci.sdk.step
 */
@Getter
public class Job {

    private final String name;

    private final List<IStep> plan = new ArrayList<>();
    @SerializedName("serial_groups")
    private final Set<String> serialGroups = new HashSet<>();
    @SerializedName("serial")
    private Boolean isSerial = false;
    @SerializedName("max_in_flight")
    private Integer maxInFlight;

    @SerializedName("public")
    private Boolean isPublic = false;

    @SerializedName("build_log_retention")
    private BuildLogRetentionPolicy buildLogRetentionPolicy;

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

    /**
     * Creates a new Job given a valid identifier.
     *
     * @param name The name of the job. This should be short; it will show up in URLs.
     * @see Validator#validateIdentifier(String)
     */
    public Job(String name) {
        Validator.validateIdentifier(name);

        this.name = name;
    }

    public Job addStep(IStep step) {
        this.plan.add(step);

        return this;
    }

    public Job markSerial() {
        this.isSerial = true;

        this.maxInFlight = 1;

        return this;
    }

    public Job addSerialGroup(String group) {
        Validator.validateIdentifier(group);

        this.isSerial = true;
        this.serialGroups.add(group);

        this.maxInFlight = 1;

        return this;
    }

    public Job setMaxInFlight(Integer maxInFlight) {
        if (maxInFlight < 0) throw new RuntimeException("Max In Flight cannot be a negative number");

        this.maxInFlight = maxInFlight;

        this.isSerial = false;
        this.serialGroups.clear();

        return this;
    }

    public Job markPublic() {
        this.isPublic = true;

        return this;
    }

    public Job setBuildLogRetention(BuildLogRetentionPolicy policy) {
        this.buildLogRetentionPolicy = policy;

        return this;
    }

    public Job setEnsure(IStep step) {
        this.ensure = step;

        return this;
    }

    public Job setOnAbort(IStep step) {
        this.onAbort = step;

        return this;
    }

    public Job setOnError(IStep step) {
        this.onError = step;

        return this;
    }

    public Job setOnFailure(IStep step) {
        this.onFailure = step;

        return this;
    }

    public Job setOnSuccess(IStep step) {
        this.onSuccess = step;

        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        return ((Job) obj).name.equals(this.name);
    }
}
