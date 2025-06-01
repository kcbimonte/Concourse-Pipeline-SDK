package com.kevinbimonte.concourse.bundled.bosh.release;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResourceType;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class BoshReleaseResourceType extends ResourceType<BoshReleaseResourceType, BoshReleaseConfig> {
    private BoshReleaseResourceType(String name) {
        super(name);
    }

    private BoshReleaseResourceType(String name, RegistryImageConfig config) {
        super(name, RegistryImageResourceType.create(), config);
    }

    public static BoshReleaseResourceType create() {
        return new BoshReleaseResourceType(Bundled.BOSH_IO_RELEASE.getTypeName());
    }

    public static BoshReleaseResourceType create(RegistryImageConfig config) {
        return new BoshReleaseResourceType(Bundled.BOSH_IO_RELEASE.getTypeName(), config);
    }

    @Override
    protected BoshReleaseResourceType getSelf() {
        return this;
    }
}
