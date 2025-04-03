package com.kevinbimonte.concourse.bundled.git;

import com.kevinbimonte.concourse.bundled.git.get.GitGet;
import com.kevinbimonte.concourse.bundled.git.put.GitPut;
import com.kevinbimonte.concourse.sdk.resource.Resource;

public class GitResource extends Resource {
    protected GitResource(String name, GitResourceType type, GitResourceConfig config) {
        super(name, type, config);
    }

    public static GitResource createResource(String name, GitResourceConfig config) {
        GitResourceType type = GitResourceType.getInstance();

        return new GitResource(name, type, config);
    }

    @Override
    public GitGet createGetDefinition() {
        return GitGet.create(this);
    }

    @Override
    public GitGet createGetDefinition(String identifier) {
        return GitGet.create(this, identifier);
    }

    @Override
    public GitPut createPutDefinition() {
        return GitPut.create(this);
    }

    @Override
    public GitPut createPutDefinition(String identifier) {
        return GitPut.create(this, identifier);
    }
}
