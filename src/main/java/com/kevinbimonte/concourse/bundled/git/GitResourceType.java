package com.kevinbimonte.concourse.bundled.git;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResourceType;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class GitResourceType extends ResourceType<GitResourceType, GitResourceConfig> {

    private GitResourceType(String name) {
        super(name);
    }

    private GitResourceType(String name, RegistryImageConfig config) {
        super(name, RegistryImageResourceType.create(), config);
    }

    public static GitResourceType create() {
        return new GitResourceType(Bundled.GIT.getTypeName());
    }

    public static GitResourceType create(RegistryImageConfig config) {
        return new GitResourceType(Bundled.GIT.getTypeName(), config);
    }

    @Override
    protected GitResourceType getSelf() {
        return this;
    }
}
