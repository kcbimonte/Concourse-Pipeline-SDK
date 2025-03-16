package org.concourseci.bundled.mock;

import org.concourseci.bundled.Bundled;
import org.concourseci.sdk.resource.ResourceType;

public class MockResourceType extends ResourceType {
    private static MockResourceType type = null;

    private MockResourceType(String name) {
        super(name);
    }


    public static MockResourceType getInstance() {
        if (type == null) {
            type = new MockResourceType(Bundled.MOCK.getTypeName());
        }

        return type;
    }
}
