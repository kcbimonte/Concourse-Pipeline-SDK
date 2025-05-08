package com.kevinbimonte.concourse.bundled.semver;

import com.kevinbimonte.concourse.sdk.resource.Resource;

public class SemVerResource extends Resource {
    protected SemVerResource(String name, SemVerResourceType type, AbstractSemverDriverConfig<?> config) {
        super(name, type, config);
    }

    public static SemVerResource createResource(String name, AbstractSemverDriverConfig<?> config) {
        SemVerResourceType type = SemVerResourceType.getInstance();

        return new SemVerResource(name, type, config);
    }

}
