package org.concourseci.bundled.docker;

import org.concourseci.sdk.resource.Resource;
import org.concourseci.sdk.resource.get.Get;
import org.concourseci.sdk.resource.put.Put;

public class DockerResource extends Resource {
    protected DockerResource(String name, DockerResourceType type, DockerConfig config) {
        super(name, type, config);
    }

    public static DockerResource createResource(String name, DockerConfig config) {
        DockerResourceType type = DockerResourceType.getInstance();

        return new DockerResource(name, type, config);
    }

    @Override
    public Get createGetDefinition() {
        return null;
    }

    @Override
    public Get createGetDefinition(String identifier) {
        return null;
    }

    @Override
    public Put createPutDefinition() {
        return null;
    }

    @Override
    public Put createPutDefinition(String identifier) {
        return null;
    }
}
