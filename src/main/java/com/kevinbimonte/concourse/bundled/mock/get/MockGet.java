package com.kevinbimonte.concourse.bundled.mock.get;

import com.kevinbimonte.concourse.bundled.mock.MockResource;
import com.kevinbimonte.concourse.sdk.resource.get.Get;
import com.kevinbimonte.concourse.sdk.util.Validator;

public class MockGet extends Get {

    private MockGet(MockResource resource) {
        super(resource);
    }

    private MockGet(MockResource resource, String name) {
        super(resource, name);
    }

    public static MockGet create(MockResource resource, String name) {
        Validator.validateIdentifier(name);

        return new MockGet(resource, name);
    }

    public static MockGet create(MockResource resource) {
        return new MockGet(resource);
    }
}
