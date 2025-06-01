package com.kevinbimonte.concourse.bundled.githubrelease;

import com.kevinbimonte.concourse.sdk.resource.Resource;

public class GithubReleaseResource extends Resource {
    protected GithubReleaseResource(String name, GithubReleaseResourceType type, GithubReleaseConfig config) {
        super(name, type, config);
    }

    public static GithubReleaseResource create(String name, GithubReleaseConfig config) {
        GithubReleaseResourceType type = GithubReleaseResourceType.create();

        return new GithubReleaseResource(name, type, config);
    }

    public static GithubReleaseResource create(String name, GithubReleaseResourceType type, GithubReleaseConfig config) {
        return new GithubReleaseResource(name, type, config);
    }
}
