package com.kevinbimonte.concourse.bundled.semver;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResourceType;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class SemverResourceType extends ResourceType<SemverResourceType, AbstractSemverDriverConfig<?>> {

    private SemverResourceType(String name) {
        super(name);
    }

    private SemverResourceType(String name, RegistryImageConfig config) {
        super(name, RegistryImageResourceType.create(), config);
    }

    public static SemverResourceType create() {
        return new SemverResourceType(Bundled.SEMVER.getTypeName());
    }

    public static SemverResourceType create(RegistryImageConfig config) {
        return new SemverResourceType(Bundled.SEMVER.getTypeName(), config);
    }

    @Override
    protected SemverResourceType getSelf() {
        return this;
    }
}
