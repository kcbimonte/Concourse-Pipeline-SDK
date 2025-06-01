package com.kevinbimonte.concourse.bundled.tracker;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

@Deprecated
public class TrackerResourceType extends ResourceType<TrackerResourceType, TrackerConfig> {
    private static TrackerResourceType type = null;

    private TrackerResourceType(String name) {
        super(name);
    }

    public static TrackerResourceType getInstance() {
        if (type == null) {
            type = new TrackerResourceType(Bundled.TRACKER.getTypeName());
        }

        return type;
    }

    @Override
    protected TrackerResourceType getSelf() {
        return this;
    }
}
