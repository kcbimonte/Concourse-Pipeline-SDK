package com.kevinbimonte.concourse.bundled.s3;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResourceType;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class S3ResourceType extends ResourceType<S3ResourceType, S3Config> {

    private S3ResourceType(String name) {
        super(name);
    }

    private S3ResourceType(String name, RegistryImageConfig config) {
        super(name, RegistryImageResourceType.create(), config);
    }

    public static S3ResourceType create() {
        return new S3ResourceType(Bundled.S3.getTypeName());
    }

    public static S3ResourceType create(RegistryImageConfig config) {
        return new S3ResourceType(Bundled.S3.getTypeName(), config);
    }

    @Override
    protected S3ResourceType getSelf() {
        return this;
    }
}
