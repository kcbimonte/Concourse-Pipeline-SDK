package com.kevinbimonte.concourse.bundled.semver;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResourceType;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class SemVerResourceType extends ResourceType<SemVerResourceType, SemVerConfig> {

    private SemVerResourceType(String name) {
        super(name);
    }

    private SemVerResourceType(String name, RegistryImageConfig config) {
        super(name, RegistryImageResourceType.create(), config);
    }

    public static SemVerResourceType create() {
        return new SemVerResourceType(Bundled.SEMVER.getTypeName());
    }

    public static SemVerResourceType create(RegistryImageConfig config) {
        return new SemVerResourceType(Bundled.SEMVER.getTypeName(), config);
    }

    @Override
    protected SemVerResourceType getSelf() {
        return this;
    }
}
