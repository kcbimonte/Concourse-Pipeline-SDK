package org.concourseci.bundled.git;

import org.concourseci.bundled.git.get.GitGet;
import org.concourseci.bundled.git.put.GitPut;
import org.concourseci.sdk.resource.Resource;

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
