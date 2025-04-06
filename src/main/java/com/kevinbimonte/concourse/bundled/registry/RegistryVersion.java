package com.kevinbimonte.concourse.bundled.registry;

import com.kevinbimonte.concourse.sdk.resource.IVersion;

public class RegistryVersion implements IVersion {
    private final String tag;
    private final String digest;

    private RegistryVersion(String tag, String digest) {
        this.tag = tag;
        this.digest = digest;
    }

    public static RegistryVersion create(String tag, String digest) {
        return new RegistryVersion(tag, digest);
    }

    public static RegistryVersion createFromTag(String tag) {
        return new RegistryVersion(tag, null);
    }

    public static RegistryVersion createFromDigest(String digest) {
        return new RegistryVersion(null, digest);
    }
}
