package org.concourseci.bundled.git;

import org.concourseci.sdk.resource.Resource;

public class GitResource extends Resource {
    protected GitResource(String name, GitResourceType type, GitConfig config) {
        super(name, type, config);
    }

    public static GitResource createResource(String name, GitConfig config) {
        GitResourceType type = GitResourceType.getInstance();

        return new GitResource(name, type, config);
    }
}
