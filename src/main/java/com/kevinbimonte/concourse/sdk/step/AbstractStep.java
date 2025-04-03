package com.kevinbimonte.concourse.sdk.step;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.util.Validator;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractStep<T extends IStep> {
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

    private Integer attempts;

    private String duration;

    private Set<String> tags;

    /**
     * @return <T> The instance of self
     */
    protected abstract T getSelf();

    /**
     * @param step
     * @return
     */
    public T setEnsure(IStep step) {
        this.ensure = step;

        return getSelf();
    }

    /**
     * @param step
     * @return
     */
    public T setOnAbort(IStep step) {
        this.onAbort = step;

        return getSelf();
    }

    /**
     * @param step
     * @return
     */
    public T setOnError(IStep step) {
        this.onError = step;

        return getSelf();
    }

    /**
     * @param step
     * @return
     */
    public T setOnFailure(IStep step) {
        this.onFailure = step;

        return getSelf();
    }

    /**
     * @param step
     * @return
     */
    public T setOnSuccess(IStep step) {
        this.onSuccess = step;

        return getSelf();
    }

    /**
     * @param attempts
     * @return
     */
    public T setAttempts(Integer attempts) {
        this.attempts = attempts;

        return getSelf();
    }

    /**
     * @param workerTag
     * @return
     */
    public T addWorkerTag(String workerTag) {
        if (this.tags == null) {
            this.tags = new HashSet<>();
        }

        this.tags.add(workerTag);

        return getSelf();
    }

    /**
     * @param duration
     * @return
     */
    public T setTimeout(String duration) {
        Validator.validateDuration(duration);

        this.duration = duration;

        return getSelf();
    }
}
