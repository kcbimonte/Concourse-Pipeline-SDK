package org.concourseci.bundled.mock;

import org.concourseci.sdk.resource.Resource;
import org.concourseci.sdk.resource.get.Get;
import org.concourseci.sdk.resource.put.Put;

public class MockResource extends Resource {
    protected MockResource(String name, MockResourceType type, MockConfig config) {
        super(name, type, config);
    }

    public static MockResource createResource(String name, MockConfig config) {
        MockResourceType type = MockResourceType.getInstance();

        return new MockResource(name, type, config);
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
