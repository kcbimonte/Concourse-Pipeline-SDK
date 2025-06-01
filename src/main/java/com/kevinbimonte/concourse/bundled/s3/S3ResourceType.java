package com.kevinbimonte.concourse.bundled.s3;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class S3ResourceType extends ResourceType<S3ResourceType, S3Config> {
    private static S3ResourceType type = null;

    private S3ResourceType(String name) {
        super(name);
    }

    public static S3ResourceType getInstance() {
        if (type == null) {
            type = new S3ResourceType(Bundled.S3.getTypeName());
        }

        return type;
    }

    @Override
    protected S3ResourceType getSelf() {
        return this;
    }
}
