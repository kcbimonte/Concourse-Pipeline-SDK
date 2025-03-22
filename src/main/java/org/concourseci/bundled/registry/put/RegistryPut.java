package org.concourseci.bundled.registry.put;

import org.concourseci.bundled.registry.RegistryImageResource;
import org.concourseci.sdk.resource.put.Put;

public class RegistryPut extends Put {
    private RegistryPut(RegistryImageResource resource) {
        super(resource);
    }

    private RegistryPut(RegistryImageResource resource, String name) {
        super(resource, name);
    }

    public static RegistryPut create(RegistryImageResource resource) {
        return new RegistryPut(resource);
    }

    public static RegistryPut create(RegistryImageResource resource, String name) {
        return new RegistryPut(resource, name);
    }
}
