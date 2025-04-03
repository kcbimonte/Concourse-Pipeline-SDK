package com.kevinbimonte.concourse.sdk.resource.put;

import com.kevinbimonte.concourse.sdk.resource.Resource;

public class NoOpPut extends Put {
    public NoOpPut(Resource resource) {
        super(resource);
    }

    public NoOpPut(Resource resource, String name) {
        super(resource, name);
    }
}
