package com.kevinbimonte.concourse.bundled.git.put;

import com.google.gson.annotations.SerializedName;
import com.kevinbimonte.concourse.bundled.git.GitResource;
import com.kevinbimonte.concourse.sdk.resource.put.Put;

public class GitPut extends Put {
    @SerializedName("params")
    private final GitPutConfig config = new GitPutConfig();

    private GitPut(GitResource resource) {
        super(resource);
    }

    private GitPut(GitResource resource, String name) {
        super(resource, name);
    }

    public static GitPut create(GitResource resource) {
        return new GitPut(resource);
    }

    public static GitPut create(GitResource resource, String name) {
        return new GitPut(resource, name);
    }
}
