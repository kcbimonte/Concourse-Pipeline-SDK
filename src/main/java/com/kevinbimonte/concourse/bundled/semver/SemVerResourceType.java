package com.kevinbimonte.concourse.bundled.semver;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class SemVerResourceType extends ResourceType<SemVerResourceType, SemVerConfig> {
    private static SemVerResourceType type = null;

    private SemVerResourceType(String name) {
        super(name);
    }

    public static SemVerResourceType getInstance() {
        if (type == null) {
            type = new SemVerResourceType(Bundled.SEMVER.getTypeName());
        }

        return type;
    }

    @Override
    protected SemVerResourceType getSelf() {
        return this;
    }
}
