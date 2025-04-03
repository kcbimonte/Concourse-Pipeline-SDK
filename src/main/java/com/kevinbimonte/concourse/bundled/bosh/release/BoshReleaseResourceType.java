package com.kevinbimonte.concourse.bundled.bosh.release;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class BoshReleaseResourceType extends ResourceType {
    private static BoshReleaseResourceType type = null;

    private BoshReleaseResourceType(String name) {
        super(name);
    }


    public static BoshReleaseResourceType getInstance() {
        if (type == null) {
            type = new BoshReleaseResourceType(Bundled.BOSH_IO_RELEASE.getTypeName());
        }

        return type;
    }
}
