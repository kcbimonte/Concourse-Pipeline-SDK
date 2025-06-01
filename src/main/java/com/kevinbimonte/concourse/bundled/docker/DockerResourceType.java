package com.kevinbimonte.concourse.bundled.docker;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageConfig;
import com.kevinbimonte.concourse.bundled.registry.RegistryImageResourceType;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class DockerResourceType extends ResourceType<DockerResourceType, DockerConfig> {

    private DockerResourceType(String name) {
        super(name);
    }

    private DockerResourceType(String name, RegistryImageConfig config) {
        super(name, RegistryImageResourceType.create(), config);
    }

    public static DockerResourceType create() {
        return new DockerResourceType(Bundled.DOCKER_IMAGE.getTypeName());
    }

    public static DockerResourceType create(RegistryImageConfig config) {
        return new DockerResourceType(Bundled.DOCKER_IMAGE.getTypeName(), config);
    }

    @Override
    protected DockerResourceType getSelf() {
        return this;
    }
}
