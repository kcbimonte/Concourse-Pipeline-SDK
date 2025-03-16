package org.concourseci.bundled.s3;

import org.concourseci.bundled.Bundled;
import org.concourseci.sdk.resource.ResourceType;

public class S3ResourceType extends ResourceType {
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
}
