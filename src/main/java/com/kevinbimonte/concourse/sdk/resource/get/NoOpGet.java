package com.kevinbimonte.concourse.sdk.resource.get;

import com.kevinbimonte.concourse.sdk.resource.IVersion;
import com.kevinbimonte.concourse.sdk.resource.Resource;

public class NoOpGet extends Get<IVersion> {
    public NoOpGet(Resource resource) {
        super(resource);
    }

    public NoOpGet(Resource resource, String name) {
        super(resource, name);
    }

    @Override
    public NoOpGet setSpecificVersion(IVersion version) {
        throw new RuntimeException("No-Op get cannot pin to specific version");
    }
}
