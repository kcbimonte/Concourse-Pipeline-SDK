package com.kevinbimonte.concourse.sdk;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.step.IStep;

public abstract class AbstractHook<T> {
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
}
