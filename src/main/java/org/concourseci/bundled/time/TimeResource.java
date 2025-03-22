package org.concourseci.bundled.time;

import org.concourseci.sdk.resource.Resource;
import org.concourseci.sdk.resource.get.Get;
import org.concourseci.sdk.resource.put.Put;

public class TimeResource extends Resource {
    protected TimeResource(String name, TimeResourceType type, TimeConfig config) {
        super(name, type, config);
    }

    public static TimeResource createResource(String name, TimeConfig config) {
        TimeResourceType type = TimeResourceType.getInstance();

        return new TimeResource(name, type, config);
    }

    @Override
    public Get createGetDefinition() {
        return null;
    }

    @Override
    public Get createGetDefinition(String identifier) {
        return null;
    }

    @Override
    public Put createPutDefinition() {
        return null;
    }

    @Override
    public Put createPutDefinition(String identifier) {
        return null;
    }
}
