package org.concourseci.bundled.registry;

import org.concourseci.sdk.resource.Resource;
import org.concourseci.sdk.resource.get.Get;
import org.concourseci.sdk.resource.put.Put;

public class RegistryImageResource extends Resource {
    protected RegistryImageResource(String name, RegistryImageResourceType type, RegistryImageConfig config) {
        super(name, type, config);
    }

    public static RegistryImageResource createResource(String name, RegistryImageConfig config) {
        RegistryImageResourceType type = RegistryImageResourceType.getInstance();

        return new RegistryImageResource(name, type, config);
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
