package org.concourseci.sdk.step;

import com.google.gson.annotations.SerializedName;

import java.util.HashSet;
import java.util.Set;

public class Do implements IStep {

    @SerializedName("do")
    private final Set<IStep> steps = new HashSet<>();

    private Do() {}

    public static Do create() {
        return new Do();
    }

    public Do addStep(IStep step) {
        this.steps.add(step);

        return this;
    }
}
