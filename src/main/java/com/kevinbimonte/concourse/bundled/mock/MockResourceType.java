package com.kevinbimonte.concourse.bundled.mock;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class MockResourceType extends ResourceType<MockResourceType, MockConfig> {
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

    @Override
    protected MockResourceType getSelf() {
        return this;
    }
}
