package com.kevinbimonte.concourse.bundled.semver.get;

import com.kevinbimonte.concourse.bundled.semver.SemVerResource;
import com.kevinbimonte.concourse.sdk.resource.get.Get;

public class SemverGet extends Get {
    private SemverGet(SemVerResource resource) {
        super(resource);
    }

    private SemverGet(SemVerResource resource, String name) {
        super(resource, name);
    }
}
