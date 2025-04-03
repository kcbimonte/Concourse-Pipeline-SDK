package com.kevinbimonte.concourse.sdk.resource.get;

import com.kevinbimonte.concourse.sdk.resource.Resource;

public class NoOpGet extends Get {
    public NoOpGet(Resource resource) {
        super(resource);
    }

    public NoOpGet(Resource resource, String name) {
        super(resource, name);
    }
}
