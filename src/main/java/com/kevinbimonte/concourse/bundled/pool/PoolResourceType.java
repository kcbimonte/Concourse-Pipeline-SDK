package com.kevinbimonte.concourse.bundled.pool;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResourceType;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class PoolResourceType extends ResourceType<PoolResourceType, PoolConfig> {

    private PoolResourceType(String name) {
        super(name);
    }

    private PoolResourceType(String name, RegistryImageConfig config) {
        super(name, RegistryImageResourceType.create(), config);
    }

    public static PoolResourceType create() {
        return new PoolResourceType(Bundled.POOL.getTypeName());
    }

    public static PoolResourceType create(RegistryImageConfig config) {
        return new PoolResourceType(Bundled.POOL.getTypeName(), config);
    }

    @Override
    protected PoolResourceType getSelf() {
        return this;
    }
}
