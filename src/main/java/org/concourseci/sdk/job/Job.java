package org.concourseci.sdk.job;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import org.concourseci.sdk.step.IStep;
import org.concourseci.sdk.util.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Job {

    @Getter
    private final String name;

    private final List<IStep> plan = new ArrayList<>();

    private Boolean serial = false;

    @SerializedName("serial_groups")
    private final Set<String> serialGroups = new HashSet<>();

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

    public Job(String name) {
        Validator.validateIdentifier(name);

        this.name = name;
    }

    public Job addStep(IStep step) {
        this.plan.add(step);

        return this;
    }

    public Job markSerial() {
        this.serial = true;

        this.maxInFlight = 1;

        return this;
    }

    public Job addSerialGroup(String group) {
        this.serial = true;
        this.serialGroups.add(group);

        this.maxInFlight = 1;

        return this;
    }

    public Job setMaxInFlight(Integer maxInFlight) {
        this.maxInFlight = maxInFlight;

        this.serial = false;
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
