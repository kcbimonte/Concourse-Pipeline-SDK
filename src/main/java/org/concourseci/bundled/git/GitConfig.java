package org.concourseci.bundled.git;

import lombok.Setter;
import org.concourseci.sdk.resource.IResourceConfig;

import java.net.URI;

public class GitConfig implements IResourceConfig {
    private final URI uri;

    @Setter
    private String branch;

    private GitConfig(URI uri) {
        this.uri = uri;
    }

    public static GitConfig create(String uri) {
        return create(uri, "main");
    }

    public static GitConfig create(String uri, String branch) {
        GitConfig config = new GitConfig(URI.create(uri));
        config.branch = branch;

        return config;
    }
}
