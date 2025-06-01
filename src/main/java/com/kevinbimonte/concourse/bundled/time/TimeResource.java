package com.kevinbimonte.concourse.bundled.time;

import com.kevinbimonte.concourse.sdk.resource.Resource;

public class TimeResource extends Resource {
    protected TimeResource(String name, TimeResourceType type, TimeConfig config) {
        super(name, type, config);
    }

    public static TimeResource create(String name, TimeConfig config) {
        TimeResourceType type = TimeResourceType.create();

        return new TimeResource(name, type, config);
    }

    public static TimeResource create(String name, TimeResourceType type, TimeConfig config) {
        return new TimeResource(name, type, config);
    }
}
