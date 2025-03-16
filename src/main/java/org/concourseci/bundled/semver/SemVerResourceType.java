package org.concourseci.bundled.semver;

import org.concourseci.bundled.Bundled;
import org.concourseci.sdk.resource.ResourceType;

public class SemVerResourceType extends ResourceType {
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
}
