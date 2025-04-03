package com.kevinbimonte.concourse.bundled.githubrelease;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

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
