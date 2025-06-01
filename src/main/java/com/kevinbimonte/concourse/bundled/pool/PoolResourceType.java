package com.kevinbimonte.concourse.bundled.pool;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class PoolResourceType extends ResourceType<PoolResourceType, PoolConfig> {
    private static PoolResourceType type = null;

    private PoolResourceType(String name) {
        super(name);
    }

    public static PoolResourceType getInstance() {
        if (type == null) {
            type = new PoolResourceType(Bundled.POOL.getTypeName());
        }

        return type;
    }

    @Override
    protected PoolResourceType getSelf() {
        return this;
    }
}
