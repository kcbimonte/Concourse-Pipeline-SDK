package com.kevinbimonte.concourse.bundled.tracker;

import com.kevinbimonte.concourse.sdk.resource.Resource;

@Deprecated
public class TrackerResource extends Resource {
    protected TrackerResource(String name, TrackerResourceType type, TrackerConfig config) {
        super(name, type, config);
    }

    public static TrackerResource create(String name, TrackerConfig config) {
        TrackerResourceType type = TrackerResourceType.create();

        return new TrackerResource(name, type, config);
    }

    public static TrackerResource create(String name, TrackerResourceType type, TrackerConfig config) {
        return new TrackerResource(name, type, config);
    }
}
