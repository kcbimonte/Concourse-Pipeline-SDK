package com.kevinbimonte.concourse.bundled.docker;

import com.kevinbimonte.concourse.sdk.resource.Resource;

public class DockerResource extends Resource {
    protected DockerResource(String name, DockerResourceType type, DockerConfig config) {
        super(name, type, config);
    }

    public static DockerResource createResource(String name, DockerConfig config) {
        DockerResourceType type = DockerResourceType.getInstance();

        return new DockerResource(name, type, config);
    }

}
