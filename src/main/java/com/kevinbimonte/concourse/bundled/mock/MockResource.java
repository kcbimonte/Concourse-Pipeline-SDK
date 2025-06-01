package com.kevinbimonte.concourse.bundled.mock;

import com.kevinbimonte.concourse.bundled.mock.get.MockGet;
import com.kevinbimonte.concourse.bundled.mock.put.MockPut;
import com.kevinbimonte.concourse.sdk.resource.Resource;

public class MockResource extends Resource {
    protected MockResource(String name, MockResourceType type, MockConfig config) {
        super(name, type, config);
    }

    public static MockResource createResource(String name, MockConfig config) {
        MockResourceType type = MockResourceType.create();

        return new MockResource(name, type, config);
    }

    @Override
    public MockGet createGetDefinition() {
        return MockGet.create(this);
    }

    @Override
    public MockGet createGetDefinition(String identifier) {
        return MockGet.create(this, identifier);
    }

    @Override
    public MockPut createPutDefinition() {
        return MockPut.create(this);
    }

    @Override
    public MockPut createPutDefinition(String identifier) {
        return MockPut.create(this, identifier);
    }
}
