package org.concourseci.bundled.semver;

import org.concourseci.sdk.resource.Resource;

public class SemVerResource extends Resource {
    protected SemVerResource(String name, SemVerResourceType type, SemVerConfig config) {
        super(name, type, config);
    }

    public static SemVerResource createResource(String name, SemVerConfig config) {
        SemVerResourceType type = SemVerResourceType.getInstance();

        return new SemVerResource(name, type, config);
    }
}
