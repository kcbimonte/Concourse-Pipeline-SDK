package com.kevinbimonte.concourse.bundled.semver.put;

import com.kevinbimonte.concourse.bundled.semver.SemVerResource;
import com.kevinbimonte.concourse.sdk.resource.put.Put;

public class SemverPut extends Put {
    private SemverPut(SemVerResource resource) {
        super(resource);
    }

    private SemverPut(SemVerResource resource, String name) {
        super(resource, name);
    }
}
