package org.concourseci.bundled.semver;

import org.concourseci.sdk.resource.Resource;
import org.concourseci.sdk.resource.get.Get;
import org.concourseci.sdk.resource.put.Put;

public class SemVerResource extends Resource {
    protected SemVerResource(String name, SemVerResourceType type, SemVerConfig config) {
        super(name, type, config);
    }

    public static SemVerResource createResource(String name, SemVerConfig config) {
        SemVerResourceType type = SemVerResourceType.getInstance();

        return new SemVerResource(name, type, config);
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
