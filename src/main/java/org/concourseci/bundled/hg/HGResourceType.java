package org.concourseci.bundled.hg;

import org.concourseci.bundled.Bundled;
import org.concourseci.sdk.resource.ResourceType;

public class HGResourceType extends ResourceType {
    private static HGResourceType type = null;

    private HGResourceType(String name) {
        super(name);
    }


    public static HGResourceType getInstance() {
        if (type == null) {
            type = new HGResourceType(Bundled.HG.getTypeName());
        }

        return type;
    }
}
