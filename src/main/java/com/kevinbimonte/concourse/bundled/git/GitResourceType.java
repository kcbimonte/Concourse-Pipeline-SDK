package com.kevinbimonte.concourse.bundled.git;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class GitResourceType extends ResourceType<GitResourceType, GitResourceConfig> {
    private static GitResourceType type = null;

    private GitResourceType(String name) {
        super(name);
    }

    public static GitResourceType getInstance() {
        if (type == null) {
            type = new GitResourceType(Bundled.GIT.getTypeName());
        }

        return type;
    }

    @Override
    protected GitResourceType getSelf() {
        return this;
    }
}
