package org.concourseci.sdk.resource.get;

import org.concourseci.sdk.resource.Resource;

public class NoOpGet extends Get {
    public NoOpGet(Resource resource) {
        super(resource);
    }

    public NoOpGet(Resource resource, String name) {
        super(resource, name);
    }
}
