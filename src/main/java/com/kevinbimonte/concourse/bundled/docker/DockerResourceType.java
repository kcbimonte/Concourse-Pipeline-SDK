package com.kevinbimonte.concourse.bundled.docker;

import com.kevinbimonte.concourse.bundled.Bundled;
import com.kevinbimonte.concourse.sdk.resource.ResourceType;

public class DockerResourceType extends ResourceType {
    private static DockerResourceType type = null;

    private DockerResourceType(String name) {
        super(name);
    }


    public static DockerResourceType getInstance() {
        if (type == null) {
            type = new DockerResourceType(Bundled.DOCKER_IMAGE.getTypeName());
        }

        return type;
    }
}
