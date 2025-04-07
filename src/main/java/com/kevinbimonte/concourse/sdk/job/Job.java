package com.kevinbimonte.concourse.sdk.job;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.AbstractHook;
import com.kevinbimonte.concourse.sdk.step.IStep;
import com.kevinbimonte.concourse.sdk.util.Validator;
import lombok.Getter;

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
 * @see com.kevinbimonte.concourse.sdk.step
 */
@Getter
public class Job extends AbstractHook<Job> {

    private String name;

    @SerializedName("old_name")
    private String oldName;

    private List<IStep> plan;
    @SerializedName("serial_groups")
    private Set<String> serialGroups;
    @SerializedName("serial")
    private Boolean isSerial;
    @SerializedName("max_in_flight")
    private Integer maxInFlight;

    @SerializedName("public")
    private Boolean isPublic;

    @SerializedName("build_log_retention")
    private BuildLogRetentionPolicy buildLogRetentionPolicy;

    @Deprecated
    @SerializedName("build_logs_to_retain")
    private Integer buildLogsToRetain;

    @SerializedName("disable_manual_trigger")
    private Boolean isManualTriggerDisabled;

    @SerializedName("interruptible")
    private Boolean isInterruptible;

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
        if (this.plan == null) {
            this.plan = new ArrayList<>();
        }

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

        if (this.serialGroups == null) {
            this.serialGroups = new HashSet<>();
        }

        this.isSerial = true;
        this.serialGroups.add(group);

        this.maxInFlight = 1;

        return this;
    }

    public Job setMaxInFlight(Integer maxInFlight) {
        if (maxInFlight < 0) {
            throw new RuntimeException("Max In Flight cannot be a negative number");
        }

        this.maxInFlight = maxInFlight;

        this.isSerial = false;

        if (this.serialGroups != null) {
            this.serialGroups.clear();
        }

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

    /**
     * Keep logs for the last specified number of builds.
     *
     * @param logsToRetain Positive Number of build logs to keep
     * @return self
     * @deprecated
     */
    @Deprecated
    public Job setBuildLogsToRetain(Integer logsToRetain) {
        if (logsToRetain < 0) {
            throw new IllegalArgumentException("Build Logs to Retain cannot be negative: " + logsToRetain);
        }

        this.buildLogsToRetain = logsToRetain;

        return this;
    }

    public Job changeName(String newName) {
        Validator.validateIdentifier(newName);

        this.oldName = this.name;
        this.name = newName;

        return this;
    }

    public Job disableManualTrigger() {
        this.isManualTriggerDisabled = true;

        return this;
    }

    public Job markInterruptable() {
        this.isInterruptible = true;

        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        return ((Job) obj).name.equals(this.name);
    }

    @Override
    protected Job getSelf() {
        return this;
    }
}
