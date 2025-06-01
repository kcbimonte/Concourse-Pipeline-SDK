package com.kevinbimonte.concourse.bundled.mock;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResourceType;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class MockResourceType extends ResourceType<MockResourceType, MockConfig> {

    private MockResourceType(String name) {
        super(name);
    }

    private MockResourceType(String name, RegistryImageConfig config) {
        super(name, RegistryImageResourceType.create(), config);
    }

    public static MockResourceType create() {
        return new MockResourceType(Bundled.MOCK.getTypeName());
    }

    public static MockResourceType create(RegistryImageConfig config) {
        return new MockResourceType(Bundled.MOCK.getTypeName(), config);
    }

    @Override
    protected MockResourceType getSelf() {
        return this;
    }
}
