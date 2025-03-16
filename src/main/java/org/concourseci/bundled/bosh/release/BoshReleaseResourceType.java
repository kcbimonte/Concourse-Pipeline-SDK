package org.concourseci.bundled.bosh.release;

import org.concourseci.bundled.Bundled;
import org.concourseci.sdk.resource.ResourceType;

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
