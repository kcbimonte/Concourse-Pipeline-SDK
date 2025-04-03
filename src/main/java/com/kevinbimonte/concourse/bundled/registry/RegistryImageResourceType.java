package com.kevinbimonte.concourse.bundled.registry;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class RegistryImageResourceType extends ResourceType {
    private static RegistryImageResourceType type = null;

    public RegistryImageResourceType(String name) {
        super(name);
    }


    public static RegistryImageResourceType getInstance() {
        if (type == null) {
            type = new RegistryImageResourceType(Bundled.REGISTRY_IMAGE.getTypeName());
        }

        return type;
    }
}
