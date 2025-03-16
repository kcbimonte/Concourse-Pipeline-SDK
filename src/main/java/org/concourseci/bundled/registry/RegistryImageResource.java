package org.concourseci.bundled.registry;

import org.concourseci.sdk.resource.Resource;

public class RegistryImageResource extends Resource {
    private RegistryImageResource(String name, RegistryImageResourceType type, RegistryImageConfig config) {
        super(name, type, config);
    }

    public static RegistryImageResource createResource(String name, RegistryImageConfig config) {
        RegistryImageResourceType type = RegistryImageResourceType.getInstance();

        return new RegistryImageResource(name, type, config);
    }
}
