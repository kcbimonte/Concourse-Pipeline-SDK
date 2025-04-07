package com.kevinbimonte.concourse.sdk.step.in_parallel;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.sdk.step.IStep;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

class InParallelConfig {
    private Set<IStep> steps;

    @Setter
    private Integer limit = null;

    @Setter
    @SerializedName("fail_fast")
    private Boolean failFast = false;


    public void addStep(IStep step) {
        if (this.steps == null) {
            this.steps = new LinkedHashSet<>();
        }

        this.steps.add(step);
    }
}
