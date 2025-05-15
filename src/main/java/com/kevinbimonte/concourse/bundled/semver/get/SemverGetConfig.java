package com.kevinbimonte.concourse.bundled.semver.get;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.bundled.semver.SemverBump;
import com.kevinbimonte.concourse.sdk.resource.get.IGetConfig;

public class SemverGetConfig implements IGetConfig {
    private SemverBump bump;

    private String build;

    private String pre;

    @SerializedName("build_without_version")
    private Boolean buildWithoutVersion;

    @SerializedName("pre_without_version")
    private Boolean preWithoutVersion;

    private SemverGetConfig() {
    }

    /**
     * Create a SemverGetConfig
     *
     * @return a new SemverGetConfig
     */
    public static SemverGetConfig create() {
        return new SemverGetConfig();
    }

    /**
     * Bump the version number semantically.
     *
     * @param bump The Bump type
     * @return self
     */
    public SemverGetConfig setBumpType(SemverBump bump) {
        this.bump = bump;

        return this;
    }

    /**
     * Same as pre but for build labels (e.g. build: foo will result in a semver of x.y.z+foo.<number>,
     * build: alpha becomes x.y.z+alpha.<number>).
     * <p>
     * It is valid for a semver to be both a prerelease and a build, for example, pre: alpha, build: test results
     * in x.y.z-alpha.<number>+test.<number>
     *
     * @param build The build string
     * @return self
     */
    public SemverGetConfig setBuild(String build) {
        this.build = build;

        return this;
    }

    /**
     * When bumping, bump to a prerelease (e.g. rc or alpha), or bump an existing prerelease.
     * <p>
     * If present, and the version is already a prerelease matching this value, its number is bumped. If the version is
     * already a prerelease of another type, (e.g. alpha vs. beta), the type is switched and the prerelease version is
     * reset to 1. If the version is not already a prerelease, then pre is added, starting at 1.
     * <p>
     * The value of pre can be anything you like; the value will be pre-pended (hah) to a numeric value. For example,
     * pre: foo will result in a semver of x.y.z-foo.<number>, pre: alpha becomes x.y.z-alpha.<number>, and
     * pre: my-preferred-naming-convention becomes x.y.z-my-preferred-naming-convention.<number>
     *
     * @param preRelease The pre-release string
     * @return self
     */
    public SemverGetConfig setPreRelease(String preRelease) {
        this.pre = preRelease;

        return this;
    }

    /**
     * By default, false, once it's set to true then Build will be bumped without a version number.
     *
     * @return self
     */
    public SemverGetConfig markBuildWithoutVersion() {
        this.buildWithoutVersion = true;

        return this;
    }

    /**
     * By default, false, once it's set to true then PreRelease will be bumped without a version number.
     *
     * @return self
     */
    public SemverGetConfig markPreWithoutVersion() {
        this.preWithoutVersion = true;

        return this;
    }
}
