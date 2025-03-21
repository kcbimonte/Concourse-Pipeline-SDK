package org.concourseci.bundled.git;

import com.google.gson.annotations.SerializedName;
import org.concourseci.sdk.resource.IResourceConfig;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class GitConfig implements IResourceConfig {
    private final URI uri;

    private String branch = null;

    private String username;

    @SerializedName("password")
    private String passwordVariable;

    private Set<String> paths;

    @SerializedName("ignore_paths")
    private Set<String> ignorePaths;

    @SerializedName("skip_ssl_verification")
    private Boolean skipSSLVerification;

    @SerializedName("tag_filter")
    private String tagFilter;

    @SerializedName("tag_regex")
    private String tagRegex;

    @SerializedName("fetch_tags")
    private Boolean fetchTags;

    private GitConfig(URI uri) {
        this.uri = uri;
    }

    public static GitConfig create(String uri) {
        return create(uri, null);
    }

    public static GitConfig create(String uri, String branch) {
        GitConfig config = new GitConfig(URI.create(uri));
        config.branch = branch;

        return config;
    }

    public GitConfig setHttpsCredentials(String username, String passwordVariable) {
        this.username = username;
        this.passwordVariable = passwordVariable;

        return this;
    }

    public GitConfig addPath(String path) {
        if (this.paths == null) {
            this.paths = new HashSet<>();
        }

        this.paths.add(path);

        return this;
    }

    public GitConfig setIgnorePaths(String path) {
        if (this.ignorePaths == null) {
            this.ignorePaths = new HashSet<>();
        }

        this.ignorePaths.add(path);

        return this;
    }

    public GitConfig skipSSLVerification() {
        this.skipSSLVerification = true;

        return this;
    }

    public GitConfig setTagFilter(String tagFilter) {
        this.tagFilter = tagFilter;

        this.tagRegex = null;

        return this;
    }

    public GitConfig setTagRegex(String tagRegex) {
        this.tagRegex = tagRegex;

        this.tagFilter = null;

        return this;
    }

    public GitConfig fetchTags() {
        this.fetchTags = true;

        return this;
    }
}
