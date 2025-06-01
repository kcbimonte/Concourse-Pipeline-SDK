package com.kevinbimonte.concourse.bundled.time;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class TimeResourceType extends ResourceType<TimeResourceType, TimeConfig> {
    private static TimeResourceType type = null;

    private TimeResourceType(String name) {
        super(name);
    }

    public static TimeResourceType getInstance() {
        if (type == null) {
            type = new TimeResourceType(Bundled.TIME.getTypeName());
        }

        return type;
    }

    @Override
    protected TimeResourceType getSelf() {
        return this;
    }
}
