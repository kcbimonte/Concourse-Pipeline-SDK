package com.kevinbimonte.concourse.bundled.git;

import com.kevinbimonte.concourse.sdk.resource.IVersion;

public class GitVersion implements IVersion {
    private final String ref;

    private GitVersion(String ref) {
        this.ref = ref;
    }

    public static GitVersion create(String ref) {
        return new GitVersion(ref);
    }
}
