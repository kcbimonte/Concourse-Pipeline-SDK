package org.concourseci.bundled.githubrelease;

import org.concourseci.bundled.Bundled;
import org.concourseci.sdk.resource.ResourceType;

public class GithubReleaseResourceType extends ResourceType {
    private static GithubReleaseResourceType type = null;

    private GithubReleaseResourceType(String name) {
        super(name);
    }


    public static GithubReleaseResourceType getInstance() {
        if (type == null) {
            type = new GithubReleaseResourceType(Bundled.GITHUB_RELEASE.getTypeName());
        }

        return type;
    }
}
