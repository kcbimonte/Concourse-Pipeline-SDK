package org.concourseci.sdk.step.task.config;

import com.google.gson.annotations.SerializedName;

public enum Platform {
    @SerializedName("linux")
    LINUX,
    @SerializedName("darwin")
    DARWIN,
    @SerializedName("windows")
    WINDOWS
}
