package com.kevinbimonte.concourse.sdk.job;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PACKAGE)
public class BuildLogRetentionPolicy {
    private Integer days;
    private Integer builds;
    @SerializedName("minimum_succeeded_builds")
    private Integer minimumSucceededBuilds;

    private BuildLogRetentionPolicy() {
    }

    public static BuildLogRetentionPolicy create() {
        return new BuildLogRetentionPolicy();
    }

    public BuildLogRetentionPolicy setDays(Integer days) {
        if (days < 0) throw new RuntimeException("Days cannot be negative");

        this.days = days;

        return this;
    }

    public BuildLogRetentionPolicy setBuilds(Integer builds) {
        if (builds < 0) throw new RuntimeException("Builds cannot be negative");

        this.builds = builds;

        return this;
    }

    public BuildLogRetentionPolicy setMinimumSucceeded(Integer minimumSuccess) {
        if (minimumSuccess < 0) throw new RuntimeException("Minimum Succeeded Builds cannot be negative");

        this.minimumSucceededBuilds = minimumSuccess;

        return this;
    }
}
