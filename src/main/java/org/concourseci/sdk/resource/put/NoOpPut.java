package org.concourseci.sdk.resource.put;

import org.concourseci.sdk.resource.Resource;

public class NoOpPut extends Put {
    public NoOpPut(Resource resource) {
        super(resource);
    }

    public NoOpPut(Resource resource, String name) {
        super(resource, name);
    }
}
