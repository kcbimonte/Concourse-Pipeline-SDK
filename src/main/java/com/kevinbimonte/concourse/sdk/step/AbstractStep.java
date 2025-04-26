package com.kevinbimonte.concourse.sdk.step;

import com.kevinbimonte.concourse.sdk.AbstractHook;
import com.kevinbimonte.concourse.sdk.util.Validator;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public abstract class AbstractStep<T extends IStep> extends AbstractHook<T> {
    private Integer attempts;

    private String timeout;

    private Set<String> tags;

    /**
     * @param attempts
     * @return
     */
    public T setAttempts(Integer attempts) {
        if (attempts < 1) {
            throw new IllegalArgumentException("Attempts cannot be less than 1");
        }

        this.attempts = attempts;

        return getSelf();
    }

    /**
     * @param workerTag
     * @return
     */
    public T addWorkerTag(String workerTag) {
        if (this.tags == null) {
            this.tags = new LinkedHashSet<>();
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

        this.timeout = duration;

        return getSelf();
    }
}
