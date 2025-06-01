package com.kevinbimonte.concourse.bundled.githubrelease;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResourceType;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class GithubReleaseResourceType extends ResourceType<GithubReleaseResourceType, GithubReleaseConfig> {
    private GithubReleaseResourceType(String name) {
        super(name);
    }

    public GithubReleaseResourceType(String name, RegistryImageConfig config) {
        super(name, RegistryImageResourceType.create(), config);
    }

    public static GithubReleaseResourceType create() {
        return new GithubReleaseResourceType(Bundled.GITHUB_RELEASE.getTypeName());
    }

    public static GithubReleaseResourceType create(RegistryImageConfig config) {
        return new GithubReleaseResourceType(Bundled.GITHUB_RELEASE.getTypeName(), config);
    }

    @Override
    protected GithubReleaseResourceType getSelf() {
        return this;
    }
}
