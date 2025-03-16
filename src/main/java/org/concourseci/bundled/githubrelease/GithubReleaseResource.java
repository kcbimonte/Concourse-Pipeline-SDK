package org.concourseci.bundled.githubrelease;

import org.concourseci.sdk.resource.Resource;

public class GithubReleaseResource extends Resource {
    private GithubReleaseResource(String name, GithubReleaseResourceType type, GithubReleaseConfig config) {
        super(name, type, config);
    }

    public static GithubReleaseResource createResource(String name, GithubReleaseConfig config) {
        GithubReleaseResourceType type = GithubReleaseResourceType.getInstance();

        return new GithubReleaseResource(name, type, config);
    }
}
