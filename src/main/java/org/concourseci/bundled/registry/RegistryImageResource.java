package org.concourseci.bundled.registry;

import org.concourseci.bundled.registry.get.RegistryGet;
import org.concourseci.bundled.registry.put.RegistryPut;
import org.concourseci.sdk.resource.Resource;

public class RegistryImageResource extends Resource {
    protected RegistryImageResource(String name, RegistryImageResourceType type, RegistryImageConfig config) {
        super(name, type, config);
    }

    public static RegistryImageResource createResource(String name, RegistryImageConfig config) {
        RegistryImageResourceType type = RegistryImageResourceType.getInstance();

        return new RegistryImageResource(name, type, config);
    }

    @Override
    public RegistryGet createGetDefinition() {
        return RegistryGet.create(this);
    }

    @Override
    public RegistryGet createGetDefinition(String identifier) {
        return RegistryGet.create(this, identifier);
    }

    @Override
    public RegistryPut createPutDefinition() {
        return RegistryPut.create(this);
    }

    @Override
    public RegistryPut createPutDefinition(String identifier) {
        return RegistryPut.create(this, identifier);
    }
}
