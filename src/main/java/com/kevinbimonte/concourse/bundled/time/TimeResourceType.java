package com.kevinbimonte.concourse.bundled.time;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResourceType;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class TimeResourceType extends ResourceType<TimeResourceType, TimeConfig> {

    private TimeResourceType(String name) {
        super(name);
    }

    private TimeResourceType(String name, RegistryImageConfig config) {
        super(name, RegistryImageResourceType.create(), config);
    }

    public static TimeResourceType create() {
        return new TimeResourceType(Bundled.TIME.getTypeName());
    }

    public static TimeResourceType create(RegistryImageConfig config) {
        return new TimeResourceType(Bundled.TIME.getTypeName(), config);
    }

    @Override
    protected TimeResourceType getSelf() {
        return this;
    }
}
