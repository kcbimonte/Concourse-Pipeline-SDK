package com.kevinbimonte.concourse.bundled.git.get;

import com.kevinbimonte.concourse.bundled.git.GitResource;
import com.kevinbimonte.concourse.bundled.git.GitVersion;
import com.kevinbimonte.concourse.sdk.resource.get.Get;

public class GitGet extends Get {

    private GitGet(GitResource resource, String name) {
        super(resource, name);
    }

    private GitGet(GitResource resource) {
        super(resource);
    }

    public static GitGet create(GitResource resource) {
        return new GitGet(resource);
    }

    public static GitGet create(GitResource resource, String name) {
        return new GitGet(resource, name);
    }

    public Get setVersion(GitVersion version) {
        return super.setVersion(version);
    }
}
