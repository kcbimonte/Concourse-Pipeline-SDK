package org.concourseci.bundled.bosh.release;

import org.concourseci.sdk.resource.Resource;
import org.concourseci.sdk.resource.get.Get;
import org.concourseci.sdk.resource.put.Put;

public class BoshReleaseResource extends Resource {
    protected BoshReleaseResource(String name, BoshReleaseResourceType type, BoshReleaseConfig config) {
        super(name, type, config);
    }

    public static BoshReleaseResource createResource(String name, BoshReleaseConfig config) {
        BoshReleaseResourceType type = BoshReleaseResourceType.getInstance();

        return new BoshReleaseResource(name, type, config);
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
