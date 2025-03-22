package org.concourseci.bundled.hg;

import org.concourseci.sdk.resource.Resource;

public class HGResource extends Resource {
    protected HGResource(String name, HGResourceType type, HGConfig config) {
        super(name, type, config);
    }

    public static HGResource createResource(String name, HGConfig config) {
        HGResourceType type = HGResourceType.getInstance();

        return new HGResource(name, type, config);
    }
}
