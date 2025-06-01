package com.kevinbimonte.concourse.bundled.tracker;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResourceType;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

@Deprecated
public class TrackerResourceType extends ResourceType<TrackerResourceType, TrackerConfig> {

    private TrackerResourceType(String name) {
        super(name);
    }

    private TrackerResourceType(String name, RegistryImageConfig config) {
        super(name, RegistryImageResourceType.create(), config);
    }

    public static TrackerResourceType create() {
        return new TrackerResourceType(Bundled.TRACKER.getTypeName());
    }

    public static TrackerResourceType create(RegistryImageConfig config) {
        return new TrackerResourceType(Bundled.TRACKER.getTypeName(), config);
    }

    @Override
    protected TrackerResourceType getSelf() {
        return this;
    }
}
