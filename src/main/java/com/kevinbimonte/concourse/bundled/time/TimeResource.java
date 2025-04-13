package com.kevinbimonte.concourse.bundled.time;

import com.kevinbimonte.concourse.bundled.time.get.TimeGet;
import com.kevinbimonte.concourse.sdk.resource.Resource;
import com.kevinbimonte.concourse.sdk.resource.put.Put;

public class TimeResource extends Resource {
    protected TimeResource(String name, TimeResourceType type, TimeConfig config) {
        super(name, type, config);
    }

    public static TimeResource createResource(String name, TimeConfig config) {
        TimeResourceType type = TimeResourceType.getInstance();

        return new TimeResource(name, type, config);
    }

    @Override
    public TimeGet createGetDefinition() {
        return TimeGet.create(this);
    }

    @Override
    public TimeGet createGetDefinition(String identifier) {
        return TimeGet.create(this, identifier);
    }

    @Override
    public Put createPutDefinition() {
        return super.createPutDefinition();
    }

    @Override
    public Put createPutDefinition(String identifier) {
        return super.createPutDefinition(identifier);
    }
}
