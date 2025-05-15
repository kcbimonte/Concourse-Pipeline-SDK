package com.kevinbimonte.concourse.bundled.semver.put;

import com.kevinbimonte.concourse.bundled.semver.SemverResource;
import com.kevinbimonte.concourse.sdk.resource.put.Put;
import com.kevinbimonte.concourse.sdk.util.Validator;

public class SemverPut extends Put {
    private SemverPut(SemverResource resource) {
        super(resource);
    }

    private SemverPut(SemverResource resource, String name) {
        super(resource, name);
    }

    public static SemverPut create(SemverResource semverResource) {
        return new SemverPut(semverResource);
    }

    public static SemverPut create(SemverResource semverResource, String identifier) {
        Validator.validateIdentifier(identifier);

        return new SemverPut(semverResource, identifier);
    }
}
