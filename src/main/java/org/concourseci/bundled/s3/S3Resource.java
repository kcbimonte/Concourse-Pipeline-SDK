package org.concourseci.bundled.s3;

import org.concourseci.sdk.resource.Resource;
import org.concourseci.sdk.resource.get.Get;
import org.concourseci.sdk.resource.put.Put;

public class S3Resource extends Resource {
    protected S3Resource(String name, S3ResourceType type, S3Config config) {
        super(name, type, config);
    }

    public static S3Resource createResource(String name, S3Config config) {
        S3ResourceType type = S3ResourceType.getInstance();

        return new S3Resource(name, type, config);
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
