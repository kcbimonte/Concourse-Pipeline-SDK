package com.kevinbimonte.concourse.bundled.registry.get;

import com.kevinbimonte.concourse.bundled.registry.RegistryImageResource;
import com.kevinbimonte.concourse.sdk.resource.get.Get;

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
