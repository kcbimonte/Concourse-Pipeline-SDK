package com.kevinbimonte.concourse.bundled.s3;

import com.kevinbimonte.concourse.sdk.resource.Resource;

public class S3Resource extends Resource {
    protected S3Resource(String name, S3ResourceType type, S3Config config) {
        super(name, type, config);
    }

    public static S3Resource create(String name, S3Config config) {
        S3ResourceType type = S3ResourceType.create();

        return new S3Resource(name, type, config);
    }

    public static S3Resource create(String name, S3ResourceType type, S3Config config) {
        return new S3Resource(name, type, config);
    }
}
