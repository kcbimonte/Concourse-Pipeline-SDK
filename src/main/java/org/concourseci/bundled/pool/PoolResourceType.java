package org.concourseci.bundled.pool;

import org.concourseci.bundled.Bundled;
import org.concourseci.sdk.resource.ResourceType;

public class PoolResourceType extends ResourceType {
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
}
