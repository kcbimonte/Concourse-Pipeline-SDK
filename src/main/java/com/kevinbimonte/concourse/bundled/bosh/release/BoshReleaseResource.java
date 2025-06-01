package com.kevinbimonte.concourse.bundled.bosh.release;

import com.kevinbimonte.concourse.sdk.resource.Resource;

public class BoshReleaseResource extends Resource {
    protected BoshReleaseResource(String name, BoshReleaseResourceType type, BoshReleaseConfig config) {
        super(name, type, config);
    }

    public static BoshReleaseResource createResource(String name, BoshReleaseConfig config) {
        BoshReleaseResourceType type = BoshReleaseResourceType.create();

        return new BoshReleaseResource(name, type, config);
    }

}
