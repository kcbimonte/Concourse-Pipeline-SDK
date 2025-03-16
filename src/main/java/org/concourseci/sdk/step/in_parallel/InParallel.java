package org.concourseci.sdk.step.in_parallel;

import com.google.gson.annotations.SerializedName;
import lombok.RequiredArgsConstructor;
import org.concourseci.sdk.step.IStep;

@RequiredArgsConstructor
public class InParallel implements IStep {
    @SerializedName("in_parallel")
    private final InParallelConfig config = new InParallelConfig();

    public InParallel addStep(IStep step) {
        this.config.addStep(step);

        return this;
    }

    public void setLimit(Integer limit) {
        this.config.setLimit(limit);
    }

    public void setFailFast(Boolean failFast) {
        this.config.setFailFast(failFast);
    }
}
