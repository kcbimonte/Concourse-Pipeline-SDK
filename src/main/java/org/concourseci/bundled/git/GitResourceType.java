package org.concourseci.bundled.git;

import org.concourseci.bundled.Bundled;
import org.concourseci.sdk.resource.ResourceType;

public class GitResourceType extends ResourceType {
    private static GitResourceType type = null;

    private GitResourceType(String name) {
        super(name);
    }


    public static GitResourceType getInstance() {
        if (type == null) {
            type = new GitResourceType(Bundled.GIT.getTypeName());
        }

        return type;
    }
}
