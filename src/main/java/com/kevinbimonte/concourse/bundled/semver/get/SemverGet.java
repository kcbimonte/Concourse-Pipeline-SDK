package com.kevinbimonte.concourse.bundled.semver.get;

import com.kevinbimonte.concourse.bundled.semver.SemverResource;
import com.kevinbimonte.concourse.sdk.resource.get.Get;
import com.kevinbimonte.concourse.sdk.util.Validator;

public class SemverGet extends Get {
    private SemverGet(SemverResource resource) {
        super(resource);
    }

    private SemverGet(SemverResource resource, String name) {
        super(resource, name);
    }

    public static SemverGet create(SemverResource semverResource) {
        return new SemverGet(semverResource);
    }

    public static SemverGet create(SemverResource semverResource, String identifier) {
        Validator.validateIdentifier(identifier);

        return new SemverGet(semverResource, identifier);
    }
}
