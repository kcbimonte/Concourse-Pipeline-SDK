package com.kevinbimonte.concourse.bundled.semver;

import com.google.gson.annotations.SerializedName;

public class GitDriverConfig extends AbstractSemverDriverConfig<GitDriverConfig> {

    private final String uri;

    private final String branch;

    private final String file;

    @SerializedName("private_key")
    private String privateKey;

    private String username;

    private String password;

    @SerializedName("git_user")
    private String gitUser;

    private Integer depth;

    @SerializedName("skip_ssl_verification")
    private Boolean skipSSLVerification;

    @SerializedName("commit_message")
    private String commitMessage;

    private GitDriverConfig(String uri, String branch, String file) {
        super(SemverDriver.GIT);

        this.uri = uri;
        this.branch = branch;
        this.file = file;
    }

    /**
     * Creates the GitDriverConfig given the required values
     *
     * @param uri The repository URL
     * @param branch The branch the file lives on
     * @param file The name of the file in the repository
     * @return A new GitDriverConfig
     */
    public static GitDriverConfig create(String uri, String branch, String file) {
        return new GitDriverConfig(uri, branch, file);
    }

    public GitDriverConfig setPrivateKey(String privateKey) {
        this.privateKey = privateKey;

        return this;
    }

    public GitDriverConfig setUsername(String username) {
        this.username = username;

        return this;
    }

    public GitDriverConfig setPassword(String password) {
        this.password = password;

        return this;
    }

    public GitDriverConfig setGitUser(String gitUser) {
        this.gitUser = gitUser;

        return this;
    }

    public GitDriverConfig setDepth(Integer depth) {
        if (depth < 1) {
            throw new IllegalArgumentException("Depth cannot be less than 1");
        }

        this.depth = depth;

        return this;
    }

    public GitDriverConfig skipSSLVerification() {
        this.skipSSLVerification = true;

        return this;
    }

    public GitDriverConfig setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;

        return this;
    }

    @Override
    protected GitDriverConfig getSelf() {
        return this;
    }
}
