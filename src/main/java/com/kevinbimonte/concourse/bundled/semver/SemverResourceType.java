package com.kevinbimonte.concourse.bundled.semver;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class SemverResourceType extends ResourceType {
    private static SemverResourceType type = null;

    private SemverResourceType(String name) {
        super(name);
    }


    public static SemverResourceType getInstance() {
        if (type == null) {
            type = new SemverResourceType(Bundled.SEMVER.getTypeName());
        }

        return type;
    }
}
