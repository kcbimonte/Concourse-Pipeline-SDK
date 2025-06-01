package com.kevinbimonte.concourse.bundled.pool;

import com.kevinbimonte.concourse.sdk.resource.Resource;

public class PoolResource extends Resource {
    protected PoolResource(String name, PoolResourceType type, PoolConfig config) {
        super(name, type, config);
    }

    public static PoolResource createResource(String name, PoolConfig config) {
        PoolResourceType type = PoolResourceType.create();

        return new PoolResource(name, type, config);
    }

}
