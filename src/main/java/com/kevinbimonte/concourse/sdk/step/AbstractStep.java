package com.kevinbimonte.concourse.sdk.step;

import com.kevinbimonte.concourse.sdk.AbstractHook;
import com.kevinbimonte.concourse.sdk.util.Validator;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractStep<T extends IStep> extends AbstractHook<T> {
    private Integer attempts;

    private String duration;

    private Set<String> tags;

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
