package com.kevinbimonte.concourse.bundled.mock;

import com.kevinbimonte.concourse.sdk.resource.Resource;

public class MockResource extends Resource {
    protected MockResource(String name, MockResourceType type, MockConfig config) {
        super(name, type, config);
    }

    public static MockResource createResource(String name, MockConfig config) {
        MockResourceType type = MockResourceType.getInstance();

        return new MockResource(name, type, config);
    }

}
