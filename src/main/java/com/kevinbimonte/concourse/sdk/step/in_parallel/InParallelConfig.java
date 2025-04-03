package com.kevinbimonte.concourse.sdk.step.in_parallel;

import com.google.gson.annotations.SerializedName;
import lombok.Setter;
import com.kevinbimonte.concourse.sdk.step.IStep;

import java.util.HashSet;
import java.util.Set;

class InParallelConfig {
    private final Set<IStep> steps = new HashSet<>();

    @Setter
    private Integer limit = null;

    @Setter
    @SerializedName("fail_fast")
    private Boolean failFast = false;


    public void addStep(IStep step) {
        this.steps.add(step);
    }
}
