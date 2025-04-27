package com.kevinbimonte.concourse.bundled.mock.put;

import com.kevinbimonte.concourse.bundled.mock.MockResource;
import com.kevinbimonte.concourse.sdk.resource.put.Put;
import com.kevinbimonte.concourse.sdk.util.Validator;

public class MockPut extends Put {
    private MockPut(MockResource resource) {
        super(resource);
    }

    private MockPut(MockResource resource, String name) {
        super(resource, name);
    }

    public static MockPut create(MockResource resource) {
        return new MockPut(resource);
    }

    public static MockPut create(MockResource resource, String name) {
        Validator.validateIdentifier(name);

        return new MockPut(resource, name);
    }
}
