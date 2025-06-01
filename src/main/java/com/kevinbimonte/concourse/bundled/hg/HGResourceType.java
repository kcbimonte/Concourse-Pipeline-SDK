package com.kevinbimonte.concourse.bundled.hg;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class HGResourceType extends ResourceType<HGResourceType, HGConfig> {
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

    @Override
    protected HGResourceType getSelf() {
        return this;
    }
}
