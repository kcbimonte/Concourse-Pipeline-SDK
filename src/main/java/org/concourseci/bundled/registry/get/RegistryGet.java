package org.concourseci.bundled.registry.get;

import org.concourseci.bundled.registry.RegistryImageResource;
import org.concourseci.sdk.resource.get.Get;

public class RegistryGet extends Get {

    private RegistryGet(RegistryImageResource resource) {
        super(resource);
    }

    private RegistryGet(RegistryImageResource resource, String name) {
        super(resource, name);
    }

    public static RegistryGet create(RegistryImageResource resource) {
        return new RegistryGet(resource);
    }

    public static RegistryGet create(RegistryImageResource resource, String name) {
        return new RegistryGet(resource, name);
    }
}
