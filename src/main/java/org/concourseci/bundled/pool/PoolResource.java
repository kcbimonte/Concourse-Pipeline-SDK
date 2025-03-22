package org.concourseci.bundled.pool;

import org.concourseci.sdk.resource.Resource;
import org.concourseci.sdk.resource.get.Get;
import org.concourseci.sdk.resource.put.Put;

public class PoolResource extends Resource {
    protected PoolResource(String name, PoolResourceType type, PoolConfig config) {
        super(name, type, config);
    }

    public static PoolResource createResource(String name, PoolConfig config) {
        PoolResourceType type = PoolResourceType.getInstance();

        return new PoolResource(name, type, config);
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
