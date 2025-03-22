package org.concourseci.bundled.git.get;

import org.concourseci.bundled.git.GitResource;
import org.concourseci.sdk.resource.get.Get;

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
}
