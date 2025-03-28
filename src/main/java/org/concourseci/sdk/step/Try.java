package org.concourseci.sdk.step;

import com.google.gson.annotations.SerializedName;

public class Try extends AbstractStep<Try> implements IStep {
    @SerializedName("try")
    private final IStep step;

    public Try(IStep step) {
        this.step = step;
    }

    @Override
    protected Try getSelf() {
        return this;
    }
}
