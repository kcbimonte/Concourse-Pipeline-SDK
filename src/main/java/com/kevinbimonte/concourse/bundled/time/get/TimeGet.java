package com.kevinbimonte.concourse.bundled.time.get;

import com.kevinbimonte.concourse.bundled.time.TimeResource;
import com.kevinbimonte.concourse.sdk.resource.Resource;
import com.kevinbimonte.concourse.sdk.resource.get.Get;

public class TimeGet extends Get {
    private TimeGet(Resource resource) {
        super(resource);
    }

    private TimeGet(Resource resource, String name) {
        super(resource, name);
    }

    public static TimeGet create(TimeResource resource) {
        return new TimeGet(resource);
    }

    public static TimeGet create(TimeResource resource, String name) {
        return new TimeGet(resource, name);
    }
}
