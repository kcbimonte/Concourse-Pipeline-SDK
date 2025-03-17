package org.concourseci.sdk.step;

import com.google.gson.annotations.SerializedName;

public class Try implements IStep {
    @SerializedName("try")
    private final IStep step;

    public Try(IStep step) {
        this.step = step;
    }
}
