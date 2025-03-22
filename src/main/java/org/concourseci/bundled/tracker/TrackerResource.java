package org.concourseci.bundled.tracker;

import org.concourseci.sdk.resource.Resource;
import org.concourseci.sdk.resource.get.Get;
import org.concourseci.sdk.resource.put.Put;

@Deprecated
public class TrackerResource extends Resource {
    protected TrackerResource(String name, TrackerResourceType type, TrackerConfig config) {
        super(name, type, config);
    }

    public static TrackerResource createResource(String name, TrackerConfig config) {
        TrackerResourceType type = TrackerResourceType.getInstance();

        return new TrackerResource(name, type, config);
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
