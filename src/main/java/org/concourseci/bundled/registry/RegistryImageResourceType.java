package org.concourseci.bundled.registry;

import org.concourseci.bundled.Bundled;
import org.concourseci.sdk.resource.ResourceType;

public class RegistryImageResourceType extends ResourceType {
    private static RegistryImageResourceType type = null;

    private RegistryImageResourceType(String name) {
        super(name);
    }


    public static RegistryImageResourceType getInstance() {
        if (type == null) {
            type = new RegistryImageResourceType(Bundled.REGISTRY_IMAGE.getTypeName());
        }

        return type;
    }
}
