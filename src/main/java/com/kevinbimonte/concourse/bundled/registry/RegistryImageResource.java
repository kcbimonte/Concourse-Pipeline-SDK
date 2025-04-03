package com.kevinbimonte.concourse.bundled.registry;

import com.kevinbimonte.concourse.bundled.registry.get.RegistryGet;
import com.kevinbimonte.concourse.bundled.registry.put.RegistryPut;
import com.kevinbimonte.concourse.sdk.resource.Resource;

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
