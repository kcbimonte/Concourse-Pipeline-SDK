package com.kevinbimonte.concourse.bundled.hg;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResourceType;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class HGResourceType extends ResourceType<HGResourceType, HGConfig> {

    private HGResourceType(String name) {
        super(name);
    }

    private HGResourceType(String name, RegistryImageConfig config) {
        super(name, RegistryImageResourceType.create(), config);
    }

    public static HGResourceType create() {
        return new HGResourceType(Bundled.HG.getTypeName());
    }

    public static HGResourceType create(RegistryImageConfig config) {
        return new HGResourceType(Bundled.HG.getTypeName(), config);
    }

    @Override
    protected HGResourceType getSelf() {
        return this;
    }
}
