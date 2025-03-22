package org.concourseci.bundled.bosh.release;

import org.concourseci.sdk.resource.Resource;

public class BoshReleaseResource extends Resource {
    protected BoshReleaseResource(String name, BoshReleaseResourceType type, BoshReleaseConfig config) {
        super(name, type, config);
    }

    public static BoshReleaseResource createResource(String name, BoshReleaseConfig config) {
        BoshReleaseResourceType type = BoshReleaseResourceType.getInstance();

        return new BoshReleaseResource(name, type, config);
    }

}
