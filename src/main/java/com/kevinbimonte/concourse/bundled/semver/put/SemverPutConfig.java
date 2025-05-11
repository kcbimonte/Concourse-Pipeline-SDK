package com.kevinbimonte.concourse.bundled.semver.put;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.bundled.semver.SemverBump;
import com.kevinbimonte.concourse.sdk.resource.put.IPutConfig;
import lombok.NonNull;

public class SemverPutConfig implements IPutConfig {
    private String file;

    private SemverBump bump;

    private String build;

    private String pre;

    @SerializedName("build_without_version")
    private Boolean buildWithoutVersion;

    @SerializedName("pre_without_version")
    private Boolean preWithoutVersion;

    @SerializedName("get_latest")
    private Boolean getLatest;

    private SemverPutConfig() {}

    public static SemverPutConfig createWithGetLatest() {
        SemverPutConfig config = new SemverPutConfig();
        config.getLatest = true;

        return config;
    }

    public static SemverPutConfig createWithFile(@NonNull String file) {
        SemverPutConfig config = new SemverPutConfig();
        config.file = file;

        return config;
    }

    public static SemverPutConfig createWithBump(@NonNull SemverBump bump) {
        SemverPutConfig config = new SemverPutConfig();
        config.bump = bump;

        return config;
    }

    public static SemverPutConfig createWithPre(@NonNull String pre) {
        SemverPutConfig config = new SemverPutConfig();
        config.pre = pre;

        return config;
    }

    public static SemverPutConfig createWithBuild(@NonNull String build) {
        SemverPutConfig config = new SemverPutConfig();
        config.build = build;

        return config;
    }

    public SemverPutConfig setBump(@NonNull SemverBump bump) {
        this.bump = bump;

        return this;
    }
}
